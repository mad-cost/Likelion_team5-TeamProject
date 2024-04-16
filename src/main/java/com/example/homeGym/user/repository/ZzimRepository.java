package com.example.homeGym.user.repository;

import com.example.homeGym.user.entity.Zzim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZzimRepository extends JpaRepository<Zzim, Long> {
    List<Zzim> findByUserId(Long userId);
}
