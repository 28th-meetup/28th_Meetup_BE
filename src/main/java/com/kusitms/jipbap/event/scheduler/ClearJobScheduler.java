package com.kusitms.jipbap.event.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 스케줄러를 통해서 Job 실행
 */
@Component
@RequiredArgsConstructor
public class ClearJobScheduler {

    private final Job eventLogClearJob;
    private final JobLauncher jobLauncher; // 스케줄링을 활용하여 Job 실행

    @Scheduled(cron = "0 3 15 * * *") // 매일 특정 시간(3시 15분)에 실행
    public void testJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis(), Long.class))
        );

        jobLauncher.run(eventLogClearJob, jobParameters);
    }
}
