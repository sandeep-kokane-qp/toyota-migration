package com.boot.qp.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "survey_id")
	private Integer surveyId;
	@Column(name = "q_text", length = 10000)
	private String qText;
	private String code;
	@Column(name = "category_id")
	private Integer categoryId;
	@Column(name = "question_id")
	private Integer questionId;
	private String type;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "q_id")
	private List<Answer> answerList = new ArrayList<>();

}
