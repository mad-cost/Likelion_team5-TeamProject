package com.example.homeGym.instructor.dto;

import com.example.homeGym.instructor.entity.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDto {
  private Long id;
  @NotNull(message = "Instructor ID cannot be null")
  private Long instructorId;

  @NotNull(message = "Category cannot be null")
  private Long mainCategoryId;

  private Long subCategoryId;

  @NotBlank(message = "Title cannot be empty")
  @Size(max = 255, message = "Title cannot be longer than 255 characters")
  private String title;

  @NotBlank(message = "Description cannot be empty")
  private String description;

  @NotBlank(message = "Supplies cannot be empty")
  private String supplies;

  @NotBlank(message = "Curriculum cannot be empty")
  private String curriculum;
  private Integer price1;
  private Integer price10;
  private Integer price20;
  private Program.ProgramState state;
  @Setter
  private String monthAmount; // id에 해당하는 프로그램의 한달 수입
  @Setter
  private String totalAmount; // id에 해당하는 프로그램의 전체 수입
  @Setter
  private Instructor instructor;

  public static ProgramDto fromEntity(Program entity){
    return ProgramDto.builder()
            .id(entity.getId())
            .instructorId(entity.getInstructorId())
            .mainCategoryId(entity.getMainCategory().getId())
            .subCategoryId(entity.getSubCategory().getId())
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
