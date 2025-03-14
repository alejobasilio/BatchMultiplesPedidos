package com.alejobasilio.batch_multiple_pedidos.launcher;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

public class JobLauncherMultiple {
	private Job borrarFicherosOutputJob;
	private Job extraccionBBDDJob;
    private Job leerProductosJob;
    private Job validarPedidosJob;
    private Job pedidosValidosToBBDDJob;
    private JobLauncher jobLauncher;
    
    public JobLauncherMultiple(JobLauncher jobLauncher, Job borrarFicherosOutputJob, Job extraccionBBDDJob, Job leerProductosJob, Job validarPedidosJob, Job pedidosValidosToBBDDJob) {
        this.jobLauncher = jobLauncher;
        this.borrarFicherosOutputJob = borrarFicherosOutputJob;
        this.extraccionBBDDJob = extraccionBBDDJob;
        this.leerProductosJob = leerProductosJob;
        this.validarPedidosJob = validarPedidosJob;
        this.pedidosValidosToBBDDJob = pedidosValidosToBBDDJob;
    }
    
    public void runJob() {
    	
    	JobParameters  jobborrarFicherosOutput=  new JobParametersBuilder()
    			.addString("ID", "Borrar Ficheros Output")
    			.addDate("date", new Date())
                .toJobParameters();
    	
    	JobParameters jobextraccionBBDD =  new JobParametersBuilder()
    			.addString("ID", "Extraccion a BBDD")
    			.addDate("date", new Date())
                .toJobParameters();
    	
    	JobParameters jobLeerProductos =  new JobParametersBuilder()
    			.addString("ID", "Leer Productos")
    			.addDate("date", new Date())
                .toJobParameters();

            JobParameters jobValidarProductos = new JobParametersBuilder()
            	.addString("ID", "Validar Pedidos")
            	.addDate("date", new Date())
                .toJobParameters();
            
            JobParameters jobPedidosValidosToBBDD =  new JobParametersBuilder()
        			.addString("ID", "Pedidos Validos a BBDD")
        			.addDate("date", new Date())
                    .toJobParameters();
            
            try {
            	jobLauncher.run(borrarFicherosOutputJob, jobborrarFicherosOutput);
            	jobLauncher.run(extraccionBBDDJob, jobextraccionBBDD);
                jobLauncher.run(leerProductosJob, jobLeerProductos);
                jobLauncher.run(validarPedidosJob, jobValidarProductos);
                jobLauncher.run(pedidosValidosToBBDDJob, jobPedidosValidosToBBDD);
            } catch (Exception e) {
                
                e.printStackTrace();
            }
    }
}
