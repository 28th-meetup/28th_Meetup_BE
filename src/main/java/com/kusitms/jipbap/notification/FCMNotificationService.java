package com.kusitms.jipbap.notification;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMNotificationService {
    private static final String API_URI = "https://fcm.googleapis.com/v1/projects/jinminboard/messages:send";

    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    public String sendNotificationByToken(FCMRequestDto dto) {
        Optional <User> user = userRepository.findById(dto.getUserId());

        if (user.isPresent()){
            log.info("fcm token" + user.get().getFcmToken());
            if (user.get().getFcmToken() != null) {

                Message message = Message.builder()
                        .setToken(user.get().getFcmToken())
                        .setNotification(Notification.builder()
                                .setTitle(dto.getTitle())
                                .setBody(dto.getBody())
                                .build())
                        .build();
                try {
                    firebaseMessaging.send(message);
                    System.out.println("알림을 성공적으로 전송했습니다. targetUserId=" + user.get().getId());
                    return "알림을 성공적으로 전송했습니다. targetUserId=" + user.get().getId();
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();;
                    return "알림 보내기를 실패했습니다. targetUserId=" + user.get().getId();
                }
            }
            else{
                return "서버에 저장된 해장 유저의 FirebaseToken이 존재하지 않습니다. targetUserId=" + user.get().getId();
            }
        }
        else{
            return "해당 유저가 존재하지 않습니다. targetUserId=" + user.get().getId();
        }

        //비동기
        //String asyncMessage = firebaseMessaging.sendAsync(message).get();
        //return asyncMessage;
    }

    public boolean isValidToken(String fcmToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(fcmToken);
            String uid = decodedToken.getUid();
            // 토큰이 검증되면 true를 반환합니다.
            return true;
        } catch (FirebaseAuthException e) {
            // 토큰이 유효하지 않으면 false를 반환합니다.
            System.out.println(e.getMessage());
            return false;
        }
    }
    //`targetToken`에 해당하는 기기로 푸시 알림 전송 요청
    // (targetToken 은 프론트 사이드에서 얻기!)

    /*
    public void sendMessageTo(String targetToken, String title, String body) throws FirebaseMessagingException, IOException, ExecutionException, InterruptedException {

        //Request + Response 사용
        //RequestBody requestBody = getRequestBody(makeFcmMessage(targetToken, title, body));
        //Request request = getRequest(requestBody);
        //Response response = getResponse(request);
        //log.info(response.body().string());

        //FirebaseMessaging 사용
        Message message = makeMessage(targetToken, title, body);
        String response = FirebaseMessaging.getInstance().send(message);
        log.info(response);

        //비동기
        String asyncMessage = FirebaseMessaging.getInstance().sendAsync(message).get();

    }

    private Response getResponse(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    private Request getRequest(RequestBody requestBody) {
        return new Request.Builder()
                .url(API_URI)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + googleCredentials.getAccessToken().getTokenValue())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();
    }

    private RequestBody getRequestBody(String message) {
        return RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
    }

    private String makeFcmMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FCMMessage fcmMessage = FCMMessage.builder()
                .message(Message.builder()
                        .setToken(targetToken)
                        .setNotification(new FCMNotification(title, body))
                        .build())
                .validateOnly(false)
                .build();

        //log.info("message 변환(Object -> String) \n" + objectMapper.writeValueAsString(fcmMessage));
        return objectMapper.writeValueAsString(fcmMessage);
    }

    private Message makeMessage(String targetToken, String title, String body) {
        FCMMessage fcmMessage = FcmMessage.builder()
                .message(Message.builder()
                        .setToken(targetToken)
                        .setNotification(new FCMNotification(title, body))
                        .build())
                .validateOnly(false)
                .build();

        return fcmMessage.getMessage();
    }
     */
}
