package com.boot.toyota.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tblQuestionMapping")
@Setter
@Getter
@NoArgsConstructor
public class TblQuestionMapping {

	@Id
	@Column(name = "TempSpecificTSQID")
	private Integer tempSpecificTSQID;
}
