package com.alejobasilio.batch_multiple_pedidos.launcher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class JobLauncherMultipleTest {

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job borrarFicherosOutputJob;

    @Mock
    private Job extraccionBBDDJob;

    @Mock
    private Job leerProductosJob;

    @Mock
    private Job validarPedidosJob;

    @Mock
    private Job pedidosValidosToBBDDJob;

    @InjectMocks
    private JobLauncherMultiple jobLauncherMultiple;

    public JobLauncherMultipleTest() {
        jobLauncherMultiple = new JobLauncherMultiple(jobLauncher, borrarFicherosOutputJob, extraccionBBDDJob, leerProductosJob, validarPedidosJob, pedidosValidosToBBDDJob);
    }

    @Test
    public void testRunJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
       
        JobParameters jobborrarFicherosOutput = new JobParametersBuilder()
                .addString("ID", "Borrar Ficheros Output")
                .addDate("date", new java.util.Date())
                .toJobParameters();

        JobParameters jobextraccionBBDD = new JobParametersBuilder()
                .addString("ID", "Extraccion a BBDD")
                .addDate("date", new java.util.Date())
                .toJobParameters();

        JobParameters jobLeerProductos = new JobParametersBuilder()
                .addString("ID", "Leer Productos")
                .addDate("date", new java.util.Date())
                .toJobParameters();

        JobParameters jobValidarProductos = new JobParametersBuilder()
                .addString("ID", "Validar Pedidos")
                .addDate("date", new java.util.Date())
                .toJobParameters();

        JobParameters jobPedidosValidosToBBDD = new JobParametersBuilder()
                .addString("ID", "Pedidos Validos a BBDD")
                .addDate("date", new java.util.Date())
                .toJobParameters();

     
        jobLauncherMultiple.runJob();

        
        verify(jobLauncher).run(borrarFicherosOutputJob, jobborrarFicherosOutput);
        verify(jobLauncher).run(extraccionBBDDJob, jobextraccionBBDD);
        verify(jobLauncher).run(leerProductosJob, jobLeerProductos);
        verify(jobLauncher).run(validarPedidosJob, jobValidarProductos);
        verify(jobLauncher).run(pedidosValidosToBBDDJob, jobPedidosValidosToBBDD);
    }
}
