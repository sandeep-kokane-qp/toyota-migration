package com.boot.mapper;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
@RequiredArgsConstructor
public class QuestionAnswerMapperRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("QuestionMapperRunner-run");
		// get all question - with answers
		// get all the question-mappings - with answer mappings

		// program to map all the question

		// log all the question that do not have categoryid and question id

	}

}
