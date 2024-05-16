package com.boot.config;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.boot.qp.entity.Question;
import com.boot.qp.repo.QuestionRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
@RequiredArgsConstructor
@Order(1)
public class ImportQuestions implements ApplicationRunner {

	private final QuestionRepo questionRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.debug("ImportQuestions.run");

		Resource resource = new ClassPathResource("toyota-survey-question.xlsx");

		List<Question> questionList = ExcelUtility.excelQuestionsToList(resource.getInputStream());
//		for (Question question : questionList) {
//			System.out.println(question);
//		}

		System.out.println(questionList.size());
		questionRepo.saveAll(questionList);
		log.info("done");
	}
}
