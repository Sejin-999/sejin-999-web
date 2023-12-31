package com.sejin999.domain.post.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "Post_Detail",
        schema = "sejin_999_test_db"
)
public class PostDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SEQ")
    private Long seq;
    @Column(name = "CONTENT" , nullable = false , length = 300)
    private String content;
    @Column(name = "POST_IMG_URL" , nullable = false , length = 500)
    private String postImgURL;
    @Column(name = "IS_CREATED" , nullable = false)
    @CreationTimestamp
    private LocalDateTime isCreated;
    @Column(name = "IS_UPDATED" , nullable = false)
    @UpdateTimestamp
    private LocalDateTime isUpdated;
    @Column(name = "IS_DELETED" , nullable = false)
    private boolean isDELETED;

    //FK
    @ManyToOne
    @JoinColumn(name = "POST_SEQ", nullable = false)
    private Post post;
}
