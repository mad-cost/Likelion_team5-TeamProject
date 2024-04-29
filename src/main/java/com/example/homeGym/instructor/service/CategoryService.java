package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.entity.MainCategory;
import com.example.homeGym.instructor.repository.MainCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
     private final MainCategoryRepository repository;
    public List<MainCategory> findAllMainCategories(){
       return repository.findAll();
    }

}
