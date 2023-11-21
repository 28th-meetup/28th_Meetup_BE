package com.kusitms.jipbap.notification;

import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "tb_notification")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id; //고유 pk

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String message;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean readYn;

    public enum NotificationType {
        OFFER, CHAT, REVIEW, WISHLIST
    }
}