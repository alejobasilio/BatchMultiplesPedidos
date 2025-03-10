package com.alejobasilio.batch_multiple_pedidos.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;
import com.alejobasilio.batch_multiple_pedidos.processor.ExtraccionBBDDProcessor;
import com.alejobasilio.batch_multiple_pedidos.reader.ExtraccionBBDDReader;
import com.alejobasilio.batch_multiple_pedidos.writer.ExtraccionBBDDWriter;



@Configuration
public class BatchConfig {

	@Bean
	public DataSource dataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
		dataSourceBuilder.url("jdbc:mysql://localhost:3306/batch");
		dataSourceBuilder.username("root");
		dataSourceBuilder.password("Lahojarota_1324");
		return dataSourceBuilder.build();
	}
	
	@Bean
	Job extraccionBBDDJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("ExtraccionBBDDJob", jobRepository).flow(extraccionBBDDStep(jobRepository,transactionManager))
				.end()
				.build();
	}
	
	@Bean
	public Step extraccionBBDDStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
	    return new StepBuilder("extraccionBBDDStep", jobRepository)
	            .<Pedido, Pedido>chunk(1, transactionManager)
	            .allowStartIfComplete(true)
	            .reader(new ExtraccionBBDDReader(dataSource()).reader())
	            .processor(new ExtraccionBBDDProcessor())
	            .writer(new ExtraccionBBDDWriter())
	            .build();
	}
}
