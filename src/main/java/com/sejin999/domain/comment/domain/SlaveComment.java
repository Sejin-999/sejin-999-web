package com.sejin999.domain.comment.domain;

import com.sejin999.domain.post.domain.Post;
import com.sejin999.domain.user.domain.User;
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
        name = "Slave_Comment",
        schema = "sejin_999_test_db"
)
public class SlaveComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SEQ")
    private Long seq;
    @Column(name = "COMMENT" , nullable = false , length = 200)
    private String comment;
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

    @ManyToOne
    @JoinColumn(name = "USER_SEQ", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "MASTER_SEQ", nullable = false)
    private MasterComment masterComment;
}
