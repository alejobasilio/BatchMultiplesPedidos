package com.alejobasilio.batch_multiple_pedidos.reader;

import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;

@Component
public class PedidosValidosToBBDDReader {

	@Bean
	public JsonItemReader<Pedido> jsonReader() {
	  return new JsonItemReaderBuilder<Pedido>()
	    .name("pedidoReader") 
	    .jsonObjectReader(new JacksonJsonObjectReader<>(Pedido.class))
	    .resource(new ClassPathResource("output/pedidos_validados.json"))
	    .saveState(true)
	    .build();
	}


}
