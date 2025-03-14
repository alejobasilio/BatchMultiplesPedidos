package com.alejobasilio.batch_multiple_pedidos.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.alejobasilio.batch_multiple_pedidos.launcher.JobLauncherMultiple;
import com.alejobasilio.batch_multiple_pedidos.model.Pedido;
import com.alejobasilio.batch_multiple_pedidos.model.Producto;
import com.alejobasilio.batch_multiple_pedidos.processor.ExtraccionBBDDProcessor;
import com.alejobasilio.batch_multiple_pedidos.processor.PedidosValidosToBBDDProcessor;
import com.alejobasilio.batch_multiple_pedidos.processor.ValidacionProductosProcessor;
import com.alejobasilio.batch_multiple_pedidos.reader.ExtraccionBBDDReader;
import com.alejobasilio.batch_multiple_pedidos.reader.PedidosPendientesReader;
import com.alejobasilio.batch_multiple_pedidos.reader.PedidosValidosToBBDDReader;
import com.alejobasilio.batch_multiple_pedidos.tasklet.BorrarFicherosOutputTasklet;
import com.alejobasilio.batch_multiple_pedidos.tasklet.LeerProductosTasklet;
import com.alejobasilio.batch_multiple_pedidos.writer.ExtraccionBBDDWriter;
import com.alejobasilio.batch_multiple_pedidos.writer.PedidosValidosToBBDDWriter;
import com.alejobasilio.batch_multiple_pedidos.writer.ValidacionProductosWriter;

@Configuration
public class BatchConfig {

	@Bean
	public List<Producto> productosValidos() {
		return new ArrayList<>();
	}

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

		return new JobBuilder("ExtraccionBBDDJob", jobRepository)
				.flow(extraccionBBDDStep(jobRepository, transactionManager))
				.end()
				.build();
	}

	@Bean
	public Step extraccionBBDDStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("extraccionBBDDStep", jobRepository)
				.<Pedido, Pedido>chunk(1, transactionManager)
				.allowStartIfComplete(true)
				.reader(new ExtraccionBBDDReader(dataSource()).reader())
				.processor(new ExtraccionBBDDProcessor()).
				writer(new ExtraccionBBDDWriter())
				.build();
	}

	@Bean
	public ValidacionProductosWriter validacionProductosWriter() {
		return new ValidacionProductosWriter();
	}

	@Bean
	public Tasklet leerProductosValidosTasklet(List<Producto> productosValidos) {
		return new LeerProductosTasklet(productosValidos);
	}

	
	@Bean
	public Job borrarFicherosOutputJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("borarraFicherosOutputJob", jobRepository).flow(
				borrarFicherosOutputStep(jobRepository, transactionManager))
				.end().build();
	}

	@Bean
	public Step borrarFicherosOutputStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("leerProductosStep", jobRepository)
				.tasklet(new BorrarFicherosOutputTasklet(), transactionManager).build();
	}
	
	
	@Bean
	public Job leerProductosJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("leerProductosJob", jobRepository).flow(
				leerProductosStep(jobRepository, transactionManager, leerProductosValidosTasklet(productosValidos())))
				.end().build();
	}

	@Bean
	public Job validarPedidosJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("validacionPedidosJob", jobRepository)
				.flow(validarPedidosStep(jobRepository, transactionManager)).end().build();
	}

	@Bean
	public Step leerProductosStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			Tasklet leerProductosValidosTasklet) {
		return new StepBuilder("leerProductosStep", jobRepository)
				.tasklet(leerProductosValidosTasklet, transactionManager).build();
	}

	@Bean
	public Step validarPedidosStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("validarPedidosStep", jobRepository).<Pedido, Pedido>chunk(10, transactionManager)
				.reader(new PedidosPendientesReader().leerPedidosPendientesReader())
				.processor(new ValidacionProductosProcessor(productosValidos()))
				.writer(validacionProductosWriter().escribirPedidosValidadosWriter()).build();
	}

	@Bean
	public Job pedidosValidosToBBDD(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("pedidosValidosToBBDDJob", jobRepository)
				.flow(pedidosValidosToBBDDStep(jobRepository, transactionManager)).end().build();
	}

	@Bean
	public Step pedidosValidosToBBDDStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("pedidosValidosToBBDDStep", jobRepository).<Pedido, Pedido>chunk(10, transactionManager)
				.reader(new PedidosValidosToBBDDReader().jsonReader()).processor(new PedidosValidosToBBDDProcessor())
				.writer(new PedidosValidosToBBDDWriter(dataSource()).writer()).build();
	}
	
	@Bean
	public JobLauncherMultiple jobLauncherMultiple(JobLauncher jobLauncher,
			@Qualifier("borrarFicherosOutputJob") Job borrarFicherosOutputJob,
			@Qualifier("extraccionBBDDJob") Job extraccionBBDDJob,
			@Qualifier("leerProductosJob") Job leerProductosJob, @Qualifier("validarPedidosJob") Job validarPedidosJob,
			@Qualifier("pedidosValidosToBBDD") Job pedidosValidosToBBDD) {
		return new JobLauncherMultiple(jobLauncher,borrarFicherosOutputJob, extraccionBBDDJob, leerProductosJob, validarPedidosJob, pedidosValidosToBBDD);
	}

	@Bean
	public CommandLineRunner commandLineRunner(JobLauncherMultiple jobLauncherMultiple) {
		return args -> {
			jobLauncherMultiple.runJob();
		};
	}

}
