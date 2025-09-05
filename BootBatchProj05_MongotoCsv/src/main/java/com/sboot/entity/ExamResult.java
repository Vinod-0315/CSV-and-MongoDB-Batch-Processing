package com.sboot.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExamResult {
	
	@Id
	private Integer id;
	private Date dob;
	private Integer semester;
	private Float percentage;
	private String gender;
	private String email;
	private String name;
	

}
