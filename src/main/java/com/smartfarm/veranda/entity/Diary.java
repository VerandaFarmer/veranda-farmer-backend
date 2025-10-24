package com.smartfarm.veranda.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "diary", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "diary_date"}) // 하루 하나만 쓸수 있게 막음
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @ManyToOne(fetch = FetchType.LAZY) // N:1 관계
    @JoinColumn(name = "user_id") // user와 조인
    private User user;

    @Column(nullable = false)
    private LocalDate diaryDate;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 255, nullable = false)
    private String imageUrl;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
