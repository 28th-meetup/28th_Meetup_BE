package com.kusitms.jipbap.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init(){
        try{
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/jipbap-notification-firebase.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    @Bean
//    public GoogleCredentials getGoogleCredentials() throws IOException {
//        return GoogleCredentials
//                .fromStream(new ClassPathResource("firebase/jinminboard-firebase-adminsdk-9ataf-770210e8eb.json").getInputStream())
//                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
//    }
//
//    @Bean
//    public FirebaseApp firebaseApp() throws IOException {
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(getGoogleCredentials())
//                .build();
//
//        return FirebaseApp.initializeApp(options);
//    }
//
//    //비동기 통신을 위함
//    @Bean
//    public ListeningExecutorService firebaseAppExecutor() {
//        return MoreExecutors.newDirectExecutorService();
//    }
//
//    @Bean
//    public OkHttpClient okHttpClient() {
//        return new OkHttpClient();
//    }
}
