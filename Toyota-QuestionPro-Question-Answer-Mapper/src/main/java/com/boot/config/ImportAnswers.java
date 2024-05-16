package com.boot.config;

import com.boot.qp.entity.Answer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

//@Component
@Slf4j
@RequiredArgsConstructor
public class ImportAnswers implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("ImportAnswers.run");

        Resource resource = new ClassPathResource("toyota-survey-answer.xlsx");

        List<Answer> answerList = ExcelUtility.excelAnswersToList(resource.getInputStream());
        for (Answer answer : answerList) {
            System.out.println(answer);
        }

        System.out.println(answerList.size());
//        questionRepo.saveAll(questionList);
//        System.out.println("done");
    }
}
