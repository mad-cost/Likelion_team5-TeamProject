package com.example.homeGym.instructor.repository;

import com.example.homeGym.instructor.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {

  List<Program> findAllByInstructorId(Long id);

  List<Program> findByInstructorId(Long instructorId);
  Optional<Program> findById(Long id);
  @Query("SELECT p FROM Program p JOIN InstructorAddress ia ON p.instructorId = ia.instructorId WHERE ia.siDo = :siDo AND ia.siGunGu = :siGunGu AND ia.dong = :dong AND p.mainCategory.id = :mainCategoryId AND (p.subCategory.id = :subCategoryId OR :subCategoryId IS NULL)")
  List<Program> findByAddressAndCategory(@Param("siDo") String siDo,
                                         @Param("siGunGu") String siGunGu,
                                         @Param("dong") String dong,
                                         @Param("mainCategoryId") Integer mainCategoryId,
                                         @Param("subCategoryId") Integer subCategoryId);
}
