package com.boot;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class VehilceUpdateRunner implements ApplicationRunner {

	private final Job job;
	private final JobLauncher jobLauncher;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis())
				.toJobParameters();
		try {
			log.info("starting job");
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("" + e.getStackTrace());
		}
	}
}
