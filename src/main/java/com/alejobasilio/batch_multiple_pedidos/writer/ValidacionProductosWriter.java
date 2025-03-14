package com.alejobasilio.batch_multiple_pedidos.writer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepListener;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;

public class ValidacionProductosWriter {
    @Bean
	public ItemWriter<Pedido> escribirPedidosValidadosWriter() {
	    return new ItemWriter<Pedido>() {
			@Override
			public void write(Chunk<? extends Pedido> items) throws Exception {
				  ObjectMapper mapper = new ObjectMapper();
		            List<Pedido> pedidos = (List<Pedido>) items.getItems();
		            File file = new File("src/main/resources/output/pedidos_validados.json");
		            File tempFile = new File("pedidos_validados_temp.json");
		            if (file.exists()) {
		                List<Pedido> pedidosExistente = mapper.readValue(file, new TypeReference<List<Pedido>>() {});
		                pedidosExistente.addAll(pedidos);
		                mapper.writeValue(tempFile, pedidosExistente);
		            } else {
		                mapper.writeValue(tempFile, pedidos);
		            }
		            if (file.exists()) {
		                file.delete();
		            }
		            tempFile.renameTo(file);
		        }
		   
	    };
	}
}
