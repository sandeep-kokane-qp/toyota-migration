package com.boot.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.boot.entity.Vehicle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableBatchProcessing
@Configuration
@Slf4j
@RequiredArgsConstructor
public class BatchConfig {

	private final DataSource dataSource;
	private final VehicleRowMapper vehicleRowMapper;
	private final VehicleItemProcessor vehicleItemProcessor;
	private final VehicleItemParameterSourceProvider itemParameterSourceProvider;

	/*
	 * @Bean ItemReader<Vehicle> itemReader() { log.info("BatchConfig-itemReader");
	 * return new
	 * JdbcPagingItemReaderBuilder<Vehicle>().dataSource(dataSource).fetchSize(100).
	 * pageSize(100) .rowMapper(vehicleRowMapper).saveState(false).queryProvider(
	 * createQueryProvider()).build(); }
	 * 
	 * private PagingQueryProvider createQueryProvider() { Map<String, Order>
	 * sortKeys = new HashMap<String, Order>(); sortKeys.put("VehicleID",
	 * Order.ASCENDING);
	 * 
	 * SqlServerPagingQueryProvider queryProvider = new
	 * SqlServerPagingQueryProvider();
	 * queryProvider.setSelectClause("VIN, VehicleID");
	 * queryProvider.setFromClause("ods.Vehicle");
	 * queryProvider.setWhereClause("WHERE ProgramID = 36 AND VehicleID > 0");
	 * queryProvider.setSortKeys(sortKeys); return queryProvider; }
	 */

	@Bean
	ItemReader<Vehicle> itemReader() {
		log.info("BatchConfig-itemReader");
		String sqlString = "SELECT VIN, VehicleID from ods.Vehicle" + " WHERE ProgramID = 36" + " AND VehicleID > 0"
				+ " ORDER BY VehicleID ASC";

		return new JdbcCursorItemReaderBuilder<Vehicle>().name("jdbc-batch-vehicle-item-reader").dataSource(dataSource)
				.sql(sqlString).rowMapper(vehicleRowMapper).build();
	}

	@Bean
	ItemWriter<Vehicle> itemWriter() {
		log.info("BatchConfig-itemWriter");
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String sqlString = "UPDATE VehicleSales.toyotapqs.srcVehicleSalesData" + " SET VehicleID = :vehicleID"
				+ " WHERE VIN = :vin";

		return new JdbcBatchItemWriterBuilder<Vehicle>().dataSource(dataSource).sql(sqlString)
				.itemSqlParameterSourceProvider(itemParameterSourceProvider).namedParametersJdbcTemplate(jdbcTemplate)
				.build();
	}

	@Bean
	Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		log.info("BatchConfig-step");
		return new StepBuilder("step-vehicle-id-update", jobRepository).<Vehicle, Vehicle>chunk(100, transactionManager)
				.reader(itemReader()).processor(vehicleItemProcessor).writer(itemWriter())
//				.taskExecutor(taskExecutor())
//				.faultTolerant().retryLimit(3).noRollback(DeadlockLoserDataAccessException.class)
//				.retry(DeadlockLoserDataAccessException.class)
				.build();
	}

	@Bean
	Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		log.info("BatchConfig-job");
		return new JobBuilder("job-vehicle-id-update", jobRepository).start(step(jobRepository, transactionManager))
				.incrementer(new RunIdIncrementer()).build();
	}

	@Bean
	TaskExecutor taskExecutor() {
		log.info("BatchConfig-taskExecutor");
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(10);
//		taskExecutor.setThreadPriority(0);
		taskExecutor.setThreadNamePrefix("BatchConfig-");
		return taskExecutor;

	}
}
