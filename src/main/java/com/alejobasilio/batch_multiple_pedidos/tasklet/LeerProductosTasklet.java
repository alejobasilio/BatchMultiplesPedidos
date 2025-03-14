package com.alejobasilio.batch_multiple_pedidos.tasklet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.ClassPathResource;

import com.alejobasilio.batch_multiple_pedidos.model.Producto;

public class LeerProductosTasklet implements Tasklet{

	private final List<Producto> productosValidos;

	public LeerProductosTasklet(List<Producto> productosValidos) {
		super();
		this.productosValidos = productosValidos;
	}



	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
            
		 try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("productos_validos.dat").getInputStream())))  {
                String line;
                while ((line = reader.readLine()) != null) {
                	 String[] campos = line.split(";");
                     Producto producto = new Producto();
                     producto.setId(Long.parseLong(campos[0]));
                     producto.setProducto(campos[1]);
                     producto.setPrecio(Double.parseDouble(campos[2]));
                     producto.setMoneda(campos[3]);
                     productosValidos.add(producto);
            }
            
            return RepeatStatus.FINISHED;
        }
	}
	}

