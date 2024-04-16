package com.example.homeGym.instructor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private Long instructorId;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Setter
    private String title;
    @Setter
    private String description;
    @Setter
    private String supplies;
    @Setter
    private String curriculum;
    @Setter
    private Integer price1;
    @Setter
    private Integer price10;
    @Setter
    private Integer price20;
    @Setter
    @Enumerated(EnumType.STRING)
    private ProgramState state;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime approvalTime;

    public enum ProgramState {
        CREATION_PENDING,  // 생성 대기중
        MODIFICATION_PENDING,  // 수정 대기중
        DELETION_PENDING,  // 삭제 대기중
        IN_PROGRESS,  // 프로그램 진행 중
        DELETION_COMPLETE   // 삭제 완료
    }
}
