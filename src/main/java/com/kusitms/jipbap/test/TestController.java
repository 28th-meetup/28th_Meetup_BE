package com.kusitms.jipbap.test;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kusitms.jipbap.auth.exception.InvalidEmailException;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import com.kusitms.jipbap.user.model.entity.User;
import com.kusitms.jipbap.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final UserRepository userRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 커넥션 테스트용 컨트롤러
     * @return 성공시 String 반환
     */
    @Operation(summary = "ec2 서버 커넥션 테스트용 컨트롤러")
    @GetMapping
    public String test() {
        return "Healthy Connection";
    }

    @Operation(summary = "ec2 서버 커넥션 테스트용 컨트롤러(인증)")
    @GetMapping("/auth")
    public String test2(@Auth AuthInfo authInfo) {
        User user = userRepository.findByEmail(authInfo.getEmail()).orElse(null);
        return "Healthy Connection";
    }

    @Operation(summary = "s3 이미지 저장, 조회 테스트용 컨트롤러")
    @PostMapping("/image")
    public String saveImageTest(@RequestParam("file") MultipartFile file) {
        try {
            return saveFile(file);
        } catch(IOException e) {
            log.info("s3 이미지 저장 테스트 중 IOException 발생");
            return null;
        }
    }


    // 파일 저장 후 uri 반환
    private String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

}
