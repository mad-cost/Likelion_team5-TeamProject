package com.example.homeGym.instructor.dto;

import com.example.homeGym.instructor.entity.Category;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.entity.UserProgram;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import com.example.homeGym.user.dto.ProgramDtoForUser;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDto {
  private Long id;
  private Long instructorId;
  private Category category;
  private String title;
  private String description;
  private String supplies;
  private String curriculum;
  private Integer price1;
  private Integer price10;
  private Integer price20;
  private Program.ProgramState state;
  @Setter
  private String monthAmount;
  @Setter
  private String totalAmount;

  public static ProgramDto fromEntity(Program entity){
    return ProgramDto.builder()
            .id(entity.getId())
            .instructorId(entity.getInstructorId())
            .category(entity.getCategory())
            .title(entity.getTitle())
            .description(entity.getDescription())
            .supplies(entity.getSupplies())
            .curriculum(entity.getCurriculum())
            .price1(entity.getPrice1())
            .price10(entity.getPrice10())
            .price20(entity.getPrice20())
            .state(entity.getState())
            .build();
  }

}
