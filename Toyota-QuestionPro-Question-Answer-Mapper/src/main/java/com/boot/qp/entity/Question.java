package com.boot.qp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "qp_question")
@Setter
@Getter
@NoArgsConstructor
public class Question {

	@Id
	private Integer id;
	private Integer categoryId;
	private Integer questionId;

}
