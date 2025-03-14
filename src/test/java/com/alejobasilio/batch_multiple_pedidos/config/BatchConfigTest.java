package com.alejobasilio.batch_multiple_pedidos.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.alejobasilio.batch_multiple_pedidos.launcher.JobLauncherMultiple;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;

@ExtendWith(MockitoExtension.class)
public class BatchConfigTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private DataSource dataSource;

    @InjectMocks
    private BatchConfig batchConfig;

    @Test
    public void testBatchConfig() {

        Job extraccionBBDDJob = batchConfig.extraccionBBDDJob(jobRepository, transactionManager);
        Job borrarFicherosOutputJob = batchConfig.borrarFicherosOutputJob(jobRepository, transactionManager);
        Job leerProductosJob = batchConfig.leerProductosJob(jobRepository, transactionManager);
        Job validarPedidosJob = batchConfig.validarPedidosJob(jobRepository, transactionManager);
        Job pedidosValidosToBBDDJob = batchConfig.pedidosValidosToBBDD(jobRepository, transactionManager);

       
        assertNotNull(extraccionBBDDJob);
        assertNotNull(borrarFicherosOutputJob);
        assertNotNull(leerProductosJob);
        assertNotNull(validarPedidosJob);
        assertNotNull(pedidosValidosToBBDDJob);
    }
    
    @Test
    public void testJobLauncherMultiple() {
        JobLauncher jobLauncher = mock(JobLauncher.class);
        Job borrarFicherosOutputJob = batchConfig.borrarFicherosOutputJob(jobRepository, transactionManager);
        Job extraccionBBDDJob = batchConfig.extraccionBBDDJob(jobRepository, transactionManager);
        Job leerProductosJob = batchConfig.leerProductosJob(jobRepository, transactionManager);
        Job validarPedidosJob = batchConfig.validarPedidosJob(jobRepository, transactionManager);
        Job pedidosValidosToBBDDJob = batchConfig.pedidosValidosToBBDD(jobRepository, transactionManager);

        JobLauncherMultiple jobLauncherMultiple = batchConfig.jobLauncherMultiple(jobLauncher, borrarFicherosOutputJob, extraccionBBDDJob, leerProductosJob, validarPedidosJob, pedidosValidosToBBDDJob);

        assertNotNull(jobLauncherMultiple);
    }

    @Test
    public void testCommandLineRunner() {
        JobLauncherMultiple jobLauncherMultiple = batchConfig.jobLauncherMultiple(mock(JobLauncher.class), 
                batchConfig.borrarFicherosOutputJob(jobRepository, transactionManager), 
                batchConfig.extraccionBBDDJob(jobRepository, transactionManager), 
                batchConfig.leerProductosJob(jobRepository, transactionManager), 
                batchConfig.validarPedidosJob(jobRepository, transactionManager), 
                batchConfig.pedidosValidosToBBDD(jobRepository, transactionManager));

        CommandLineRunner commandLineRunner = batchConfig.commandLineRunner(jobLauncherMultiple);

        assertNotNull(commandLineRunner);
    }
}
