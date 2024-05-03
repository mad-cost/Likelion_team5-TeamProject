package com.example.homeGym.instructor.repository;

import com.example.homeGym.instructor.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT DISTINCT a.siDo FROM Address a ORDER BY a.siDo ASC")
    List<String> findDistinctSiDo();

    List<Address> findBySiDoOrderBySiGunGuAsc(String siDo);

    List<Address> findBySiGunGuOrderByDongAsc(String siGunGu);
}
