package com.alejobasilio.batch_multiple_pedidos.writer;

import java.io.File;
import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
					List<Pedido> pedidosExistente = mapper.readValue(file, new TypeReference<List<Pedido>>() {
					});
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
