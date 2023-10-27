package com.kusitms.jipbap.message;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub"); //message broker가 subscriber들에게 메세지를 전달할 url (구독 요청)
        config.setApplicationDestinationPrefixes("/pub");  //클라이언트가 서버로 메세지를 보낼 url 접두사 지정 (발행 요청)
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp") //websocket 연결 endpoint
                .setAllowedOriginPatterns("*");
//                .withSockJS(); //제거시 endpoint 연결 성공
    }
}