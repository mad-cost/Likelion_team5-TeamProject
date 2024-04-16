package com.example.homeGym.user.repository;

import com.example.homeGym.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
