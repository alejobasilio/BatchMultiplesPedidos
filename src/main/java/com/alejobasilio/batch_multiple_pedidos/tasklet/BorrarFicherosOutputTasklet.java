package com.alejobasilio.batch_multiple_pedidos.tasklet;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class BorrarFicherosOutputTasklet implements Tasklet{

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		File csv = new File("src/main/resources/output/pedidos_validados.json");
		File json = new File("src/main/resources/output/pedidos_validados.json");
		
		if (csv.exists()) {
			csv.delete();
		}
		if(json.exists()) {
			json.delete();
		}
		return RepeatStatus.FINISHED;
	}

}
