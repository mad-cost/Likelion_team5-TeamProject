package com.example.homeGym.order.repo;

import com.example.homeGym.order.entity.ProgramOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<ProgramOrder, Long> {


}
