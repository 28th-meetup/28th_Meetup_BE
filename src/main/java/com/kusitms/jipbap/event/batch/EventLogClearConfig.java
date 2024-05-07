package com.kusitms.jipbap.event.batch;

import com.kusitms.jipbap.event.aop.EventLog;
import com.kusitms.jipbap.event.aop.EventLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class EventLogClearConfig {

    private final String JOB_NAME = "eventLogClearJob";
    private final String STEP_NAME = "eventLogClearStep";

    private final EventLogRepository eventLogRepository;

    /**
     * Job 등록
     */
    @Bean
    public Job eventLogClearJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer()) // job parameter - sequential id
                .start(eventLogClearStep(jobRepository, transactionManager)) // step 설정
                .build();
    }

    /**
     * Step 등록
     */
    @Bean
    @JobScope
    public Step eventLogClearStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<EventLog, EventLog>chunk(500, transactionManager) // chunkSize: 몇 개 단위로 데이터를 처리할 것인지 지정. 참고로 파라미터로 받아서 사용할 수는 없다.
                .reader(eventLogClearReader())
                .processor(eventLogClearProcessor())
                .writer(eventLogClearWriter())
                .build();
    }

    /**
     * 실제 데이터 제거
     */
    @Bean
    @StepScope
    public RepositoryItemWriter<EventLog> eventLogClearWriter() {

        return new RepositoryItemWriterBuilder<EventLog>()
                .repository(eventLogRepository)
                .methodName("delete")
                .build();
    }

    /**
     * 데이터 가공 : 현재는 의미없음
     */
    @Bean
    @StepScope
    public ItemProcessor<EventLog, EventLog> eventLogClearProcessor() {
        return item -> item;
    }

    /**
     * tb_eventlog 테이블의 일정 기간 이상 된 데이터 제거
     * Spring Batch는 QuerydslItemReader를 지원하지 않는다.
     * RepositoryItemReader의 argument는 두번째 인자로 PageRequest객체를 반드시 받도록 되어있는데, 이유를 잘 모르겠다
     */
    @Bean
    @StepScope
    public ItemReader<EventLog> eventLogClearReader() {

        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        log.info("EventLog 삭제 기준 DateTime : {}", dateTime);

        return new RepositoryItemReaderBuilder<EventLog>()
                .name("eventLogClearReader")
                .repository(eventLogRepository)
                .methodName("findByCreatedAtBefore")
                .arguments(List.of(dateTime)) // 의미없는 PageRequest 전달
                .pageSize(500) // chunkSize와 일치하게 설정
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();

    }
}
