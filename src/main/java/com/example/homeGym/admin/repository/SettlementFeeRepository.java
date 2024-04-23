package com.example.homeGym.admin.repository;

import com.example.homeGym.admin.entity.SettlementFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementFeeRepository extends JpaRepository<SettlementFee, Long > {
    SettlementFee findByInstructorId(Long instructorId);
}
