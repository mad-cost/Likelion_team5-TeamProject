//package com.example.homeGym.order;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDateTime;
//
//// Base Entity 분리
//
//@Getter
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
//public class BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @CreatedDate
//    @Column(nullable = false)
//    private LocalDateTime createAt;
//}
