package com.example.homeGym.user.repository;

import com.example.homeGym.user.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    List<Apply> findAllByProgramIdAndApplyState(Long programId, Apply.ApplyState state);
}
