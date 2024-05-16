package com.boot.qp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "qp_answer")
@Setter
@Getter
@NoArgsConstructor
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "survey_id")
	private Integer surveyId;
	private String code;
	@Column(name = "q_id")
	private Integer qId;
	@Column(name = "a_text", length = 10000)
	private String aText;
	@Column(name = "answer_code")
	private String answerCode;

}
