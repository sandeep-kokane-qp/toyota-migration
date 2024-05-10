package com.boot.config;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlServerPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
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

	@Bean
	ItemReader<Vehicle> itemReader() {
		log.info("BatchConfig-itemReader");
		return new JdbcPagingItemReaderBuilder<Vehicle>().dataSource(dataSource).fetchSize(500).pageSize(500)
				.rowMapper(vehicleRowMapper).saveState(false).queryProvider(createQueryProvider()).build();
	}

	private PagingQueryProvider createQueryProvider() {
		SqlServerPagingQueryProvider queryProvider = new SqlServerPagingQueryProvider();
		queryProvider.setSelectClause("srcVehicleSalesDataID, vh.VehicleID AS vehicleId");
		queryProvider.setFromClause("VehicleSales.toyotapqs.srcVehicleSalesData AS src "
				+ " JOIN VehicleSales.ods.Vehicle AS vh ON vh.VIN = src.VIN AND vh.ProgramID=36");
		queryProvider.setWhereClause("WHERE srcVehicleSalesDataID > 0");

		Map<String, Order> sortKeys = new HashMap<String, Order>();
		sortKeys.put("srcVehicleSalesDataID", Order.ASCENDING);

		queryProvider.setSortKeys(sortKeys);
		return queryProvider;
	}

	/*
	 * @Bean ItemReader<Vehicle> itemReader() { log.info("BatchConfig-itemReader");
	 * 
	 * String sqlString =
	 * "SELECT src.srcVehicleSalesDataID as srcSalesDataId, src.VIN as vin, vh.VehicleID as vehicleId"
	 * + "  FROM VehicleSales.toyotapqs.srcVehicleSalesData src" +
	 * "  JOIN VehicleSales.ods.Vehicle vh ON vh.VIN = src.VIN AND vh.ProgramID=36"
	 * + " WHERE src.srcVehicleSalesDataID > 0 " +
	 * " ORDER BY src.srcVehicleSalesDataID ASC";
	 * 
	 * return new
	 * JdbcCursorItemReaderBuilder<Vehicle>().name("jdbc-batch-vehicle-item-reader")
	 * .dataSource(dataSource) .sql(sqlString).rowMapper(vehicleRowMapper).build();
	 * }
	 */

	@Bean
	ItemWriter<Vehicle> itemWriter() {
		log.info("BatchConfig-itemWriter");
		String sqlString = "UPDATE VehicleSales.toyotapqs.srcVehicleSalesData" + " SET VehicleID = :vehicleID"
				+ " WHERE srcVehicleSalesDataID = :srcVehicleSalesDataID";

		return new JdbcBatchItemWriterBuilder<Vehicle>().dataSource(dataSource).sql(sqlString)
				.itemSqlParameterSourceProvider(itemParameterSourceProvider).build();
	}

	@Bean
	Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		log.info("BatchConfig-step");
		return new StepBuilder("step-vehicle-id-update", jobRepository).<Vehicle, Vehicle>chunk(500, transactionManager)
				.reader(itemReader()).processor(vehicleItemProcessor).writer(itemWriter()).taskExecutor(taskExecutor())
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
		taskExecutor.setConcurrencyLimit(20);
		taskExecutor.setThreadNamePrefix("BatchConfig-");
		return taskExecutor;
	}
}
