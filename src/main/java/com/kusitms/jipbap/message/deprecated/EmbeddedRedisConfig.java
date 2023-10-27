//package com.kusitms.jipbap.message;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import redis.embedded.RedisServer;
//
//@Configuration
//public class EmbeddedRedisConfig {
//
//    @Value("${spring.data.redis.port}")
//    private int redisPort;
//
//    private RedisServer redisServer;
//
//    @PostConstruct
//    public void redisServer() {
//        redisServer = RedisServer.builder()
//                        .port(6380)
//                        .build();
//        redisServer.start(); //doesn't work in macos 14 sonoma
//    }
//
//    @PreDestroy
//    public void stopRedis() {
//        if (redisServer != null) {
//            redisServer.stop();
//        }
//    }
//}