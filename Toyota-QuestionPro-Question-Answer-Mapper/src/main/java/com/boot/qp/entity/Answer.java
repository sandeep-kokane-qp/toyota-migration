package com.boot.qp.entity;

import jakarta.persistence.Entity;
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
	private Integer id;
	private Integer surveyId;
	private String code;
	private Integer qId;
	private String aText;

}
