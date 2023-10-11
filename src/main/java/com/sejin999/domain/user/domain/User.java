package com.sejin999.domain.user.domain;

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
        name = "User",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_id", columnNames = {"ID"})
        },
        schema = "sejin_999_test_db"
)


@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SEQ")
    private Long seq;
    @Column(name = "ID" , nullable = false , length = 30 , unique = true)
    private String id;
    @Column(name = "PASSWORD" , nullable = false , length = 200)
    private String password;
    @Column(name = "NICKNAME" , nullable = false , length = 10)
    private String nickName;
    @Column(name = "IS_CREATED" , nullable = false)
    @CreationTimestamp
    private LocalDateTime isCreated;
    @Column(name = "IS_UPDATED" , nullable = false)
    @UpdateTimestamp
    private LocalDateTime isUpdated;
    @Column(name = "IS_DELETED" , nullable = false)
    private boolean isDELETED;


}
