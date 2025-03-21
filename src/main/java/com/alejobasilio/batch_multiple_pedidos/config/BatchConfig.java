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

/**
 * Clase de configuración para la aplicación de procesamiento por lotes.
 * 
 * @author Alejo
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class BatchConfig {

	@Bean
	 List<Producto> productosValidos() {
		return new ArrayList<>();
	}

    /**
     * Crea un objeto DataSource que se utiliza para conectarse a la base de datos.
     * 
     * @return el objeto DataSource configurado.
     */
	@Bean
	 DataSource dataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
		dataSourceBuilder.url("jdbc:mysql://localhost:3306/batch");
		dataSourceBuilder.username("root");
		dataSourceBuilder.password("Lahojarota_1324");
		return dataSourceBuilder.build();
	}

    /**
     * Crea un objeto Job que se utiliza para definir el flujo de trabajo de extracción de datos de la base de datos.
     * 
     * @param jobRepository el repositorio de trabajos que se utiliza para almacenar la información de los trabajos.
     * @param transactionManager el administrador de transacciones que se utiliza para gestionar las transacciones de la base de datos.
     * @return el objeto Job configurado.
     */
	@Bean
	Job extraccionBBDDJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

		return new JobBuilder("ExtraccionBBDDJob", jobRepository)
				.flow(extraccionBBDDStep(jobRepository, transactionManager))
				.end()
				.build();
	}

    /**
     * Crea un objeto Step que se utiliza para definir el paso de extracción de datos de la base de datos.
     * 
     * @param jobRepository el repositorio de trabajos que se utiliza para almacenar la información de los trabajos.
     * @param transactionManager el administrador de transacciones que se utiliza para gestionar las transacciones de la base de datos.
     * @return el objeto Step configurado.
     */
	@Bean
	 Step extraccionBBDDStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("extraccionBBDDStep", jobRepository)
				.<Pedido, Pedido>chunk(1, transactionManager)
				.allowStartIfComplete(true)
				.reader(new ExtraccionBBDDReader(dataSource()).reader())
				.processor(new ExtraccionBBDDProcessor()).
				writer(new ExtraccionBBDDWriter())
				.build();
	}

	@Bean
	 ValidacionProductosWriter validacionProductosWriter() {
		return new ValidacionProductosWriter();
	}

	@Bean
	 Tasklet leerProductosValidosTasklet(List<Producto> productosValidos) {
		return new LeerProductosTasklet(productosValidos);
	}

    /**
     * Crea un objeto Job que se utiliza para definir el flujo de trabajo de borrado de ficheros de salida.
     * 
     * @param jobRepository el repositorio de trabajos que se utiliza para almacenar la información de los trabajos.
     * @param transactionManager el administrador de transacciones que se utiliza para gestionar las transacciones de la base de datos.
     * @return el objeto Job configurado.
     */
	@Bean
	 Job borrarFicherosOutputJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("borarraFicherosOutputJob", jobRepository).flow(
				borrarFicherosOutputStep(jobRepository, transactionManager))
				.end().build();
	}

    /**
     * Crea un objeto Step que se utiliza para definir el paso de borrado de ficheros de salida.
     * 
     * @param jobRepository el repositorio de trabajos que se utiliza para almacenar la información de los trabajos.
     * @param transactionManager el administrador de transacciones que se utiliza para gestionar las transacciones de la base de datos.
     * @return el objeto Step configurado.
     */
	@Bean
	 Step borrarFicherosOutputStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("leerProductosStep", jobRepository)
				.tasklet(new BorrarFicherosOutputTasklet(), transactionManager).build();
	}
	
	/**
     * Crea un objeto Job que se utiliza para definir el flujo de trabajo de lectura de productos.
     * 
     * @param jobRepository el repositorio de trabajos que se utiliza para almacenar la información de los trabajos.
     * @param transactionManager el administrador de transacciones que se utiliza para gestionar las transacciones de la base de datos.
     * @return el objeto Job configurado.
     */
    @Bean
    Job leerProductosJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("leerProductosJob", jobRepository).flow(
                leerProductosStep(jobRepository, transactionManager, leerProductosValidosTasklet(productosValidos())))
                .end().build();
    }

    /**
     * Crea un objeto Job que se utiliza para definir el flujo de trabajo de validación de pedidos.
     * 
     * @param jobRepository el repositorio de trabajos que se utiliza para almacenar la información de los trabajos.
     * @param transactionManager el administrador de transacciones que se utiliza para gestionar las transacciones de la base de datos.
     * @return el objeto Job configurado.
     */
    @Bean
    Job validarPedidosJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("validacionPedidosJob", jobRepository)
                .flow(validarPedidosStep(jobRepository, transactionManager)).end().build();
    }

    /**
     * Crea un objeto Step que se utiliza para definir el paso de lectura de productos.
     * 
     * @param jobRepository el repositorio de trabajos que se utiliza para almacenar la información de los trabajos.
     * @param transactionManager el administrador de transacciones que se utiliza para gestionar las transacciones de la base de datos.
     * @param leerProductosValidosTasklet el objeto Tasklet que se utiliza para leer los productos válidos.
     * @return el objeto Step configurado.
     */
    @Bean
    Step leerProductosStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            Tasklet leerProductosValidosTasklet) {
        return new StepBuilder("leerProductosStep", jobRepository)
                .tasklet(leerProductosValidosTasklet, transactionManager).build();
    }

    /**
     * Crea un objeto Step que se utiliza para definir el paso de validación de pedidos.
     * 
     * @param jobRepository el repositorio de trabajos que se utiliza para almacenar la información de los trabajos.
     * @param transactionManager el administrador de transacciones que se utiliza para gestionar las transacciones de la base de datos.
     * @return el objeto Step configurado.
     */
    @Bean
    Step validarPedidosStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("validarPedidosStep", jobRepository)
                .<Pedido, Pedido> chunk(10, transactionManager)
                .reader(new PedidosPendientesReader().leerPedidosPendientesReader())
                .processor(new ValidacionProductosProcessor(productosValidos()))
                .writer(validacionProductosWriter().escribirPedidosValidadosWriter()).build();
    }

    /**
     * Crea un objeto Job que se utiliza para definir el flujo de trabajo de inserción de pedidos válidos en la base de datos.
     * 
     * @param jobRepository el repositorio de trabajos que se utiliza para almacenar la información de los trabajos.
     * @param transactionManager el administrador de transacciones que se utiliza para gestionar las transacciones de la base de datos.
     * @return el objeto Job configurado.
     */
    @Bean
    Job pedidosValidosToBBDD(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("pedidosValidosToBBDDJob", jobRepository)
                .flow(pedidosValidosToBBDDStep(jobRepository, transactionManager)).end().build();
    }

    /**
     * Crea un objeto Step que se utiliza para definir el paso de inserción de pedidos válidos en la base de datos.
     * 
     * @param jobRepository el repositorio de trabajos que se utiliza para almacenar la información de los trabajos.
     * @param transactionManager el administrador de transacciones que se utiliza para gestionar las transacciones de la base de datos.
     * @return el objeto Step configurado.
     */
    @Bean
    Step pedidosValidosToBBDDStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("pedidosValidosToBBDDStep", jobRepository)
                .<Pedido, Pedido> chunk(10, transactionManager)
                .reader(new PedidosValidosToBBDDReader().jsonReader())
                .processor(new PedidosValidosToBBDDProcessor())
                .writer(new PedidosValidosToBBDDWriter(dataSource()).writer()).build();
    }

    /**
     * Crea un objeto JobLauncherMultiple que se utiliza para lanzar los trabajos de forma secuencial.
     * 
     * @param jobLauncher el objeto JobLauncher que se utiliza para lanzar los trabajos.
     * @param borrarFicherosOutputJob el objeto Job que se utiliza para borrar los ficheros de salida.
     * @param extraccionBBDDJob el objeto Job que se utiliza para extraer los datos de la base de datos.
     * @param leerProductosJob el objeto Job que se utiliza para leer los productos.
     * @param validarPedidosJob el objeto Job que se utiliza para validar los pedidos.
     * @param pedidosValidosToBBDD el objeto Job que se utiliza para insertar los pedidos válidos en la base de datos.
     * @return el objeto JobLauncherMultiple configurado.
     */
    @Bean
    JobLauncherMultiple jobLauncherMultiple(JobLauncher jobLauncher,
            @Qualifier("borrarFicherosOutputJob") Job borrarFicherosOutputJob,
            @Qualifier("extraccionBBDDJob") Job extraccionBBDDJob,
            @Qualifier("leerProductosJob") Job leerProductosJob, @Qualifier("validarPedidosJob") Job validarPedidosJob,
            @Qualifier("pedidosValidosToBBDD") Job pedidosValidosToBBDD) {
        return new JobLauncherMultiple(jobLauncher, borrarFicherosOutputJob, extraccionBBDDJob, leerProductosJob, validarPedidosJob, pedidosValidosToBBDD);
    }

    /**
     * Crea un objeto CommandLineRunner que se utiliza para lanzar los trabajos de forma secuencial al iniciar la aplicación.
     * 
     * @param jobLauncherMultiple el objeto JobLauncherMultiple que se utiliza para lanzar los trabajos.
     * @return el objeto CommandLineRunner configurado.
     */
    @Bean
    CommandLineRunner commandLineRunner(JobLauncherMultiple jobLauncherMultiple) {
        return args -> {
            jobLauncherMultiple.runJob();
            System.exit(0);
        };
    }

}
