package com.kusitms.jipbap.post;

import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.user.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="post_id")
    private Long id; //고유 pk

    @Enumerated(EnumType.STRING)
    private PostType postType;

    private String title;
    private String content;
    private Long hit;

    private LocalDateTime createdBy;
    private LocalDateTime updatedBy;


    public enum PostType {
        STANDBY, APPROVE, SUSPEND
    }
}