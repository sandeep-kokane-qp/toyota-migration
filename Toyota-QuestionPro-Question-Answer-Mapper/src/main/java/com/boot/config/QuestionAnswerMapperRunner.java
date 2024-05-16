package com.boot.config;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.boot.qp.entity.Question;
import com.boot.qp.repo.QuestionRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class QuestionAnswerMapperRunner implements ApplicationRunner {

	private final QuestionRepo questionRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("QuestionMapperRunner-run");
		// get all question - with answers
		// all question without static presentation text questions
		List<Question> questionList = questionRepo.findAll().stream().filter(item -> !"Q".equals(item.getType()))
				.toList();
		// get all the question-mappings - with answer mappings

		log.info("Question list :: " + questionList.size());

//		for (Question question : questionList) {
//			System.out.println(question);
//		}

		// program to map all the question

		// log all the question that do not have categoryid and question id

	}

}
