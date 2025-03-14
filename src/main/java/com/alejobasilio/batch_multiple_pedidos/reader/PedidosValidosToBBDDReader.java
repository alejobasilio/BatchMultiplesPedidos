package com.alejobasilio.batch_multiple_pedidos.reader;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;
import com.fasterxml.jackson.databind.ObjectMapper;

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
