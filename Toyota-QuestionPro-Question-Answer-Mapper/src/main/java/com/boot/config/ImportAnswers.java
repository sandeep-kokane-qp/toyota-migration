package com.boot.config;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.boot.qp.entity.Answer;
import com.boot.qp.repo.AnswerRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
@RequiredArgsConstructor
@Order(2)
public class ImportAnswers implements ApplicationRunner {

	private final AnswerRepo answerRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.debug("ImportAnswers.run");

		Resource resource = new ClassPathResource("toyota-survey-answer.xlsx");

		List<Answer> answerList = ExcelUtility.excelAnswersToList(resource.getInputStream());

		System.out.println(answerList.size());
		answerRepo.saveAll(answerList);
		log.info("done");
	}
}
