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
        name = "Index",
        uniqueConstraints = {
                @UniqueConstraint(name = "title", columnNames = {"title"})
        },
        schema = "sejin_999_test_db"
)
public class Index {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="SEQ")
        private Long seq;
        @Column(name = "TITLE" , nullable = false , length = 10)
        private String title;
        @Column(name = "CONTENT" , nullable = false , length = 200)
        private String content;
        @Column(name = "IS_CREATED" , nullable = false)
        @CreationTimestamp
        private LocalDateTime isCreated;
        @Column(name = "IS_UPDATED" , nullable = false)
        @UpdateTimestamp
        private LocalDateTime isUpdated;
        @Column(name = "IS_DELETED" , nullable = false)
        private boolean isDELETED;
}