package com.boot.qp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.qp.entity.Question;

public interface QuestionRepo extends JpaRepository<Question, Integer> {

}
