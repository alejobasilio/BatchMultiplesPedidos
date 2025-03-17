package com.alejobasilio.batch_multiple_pedidos.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;

/**
 * Lector de pedidos pendientes que se utiliza para leer los pedidos pendientes desde un archivo CSV.
 * 
 * @author Alejo
 * @version 1.0
 * @since 1.0
 */
public class PedidosPendientesReader extends FlatFileItemReader<Pedido>{

	@Bean
	public FlatFileItemReader<Pedido> leerPedidosPendientesReader() {
		 FlatFileItemReader<Pedido> reader = new FlatFileItemReader<>();
		    reader.setResource(new ClassPathResource("output/pedidos_pendientes.csv"));
		    reader.setLinesToSkip(1); 
		  

		    DefaultLineMapper<Pedido> lineMapper = new DefaultLineMapper<>();
		    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		    tokenizer.setNames("id", "cliente", "id_producto", "importe");
		    tokenizer.setDelimiter(";");

		    BeanWrapperFieldSetMapper<Pedido> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		    fieldSetMapper.setTargetType(Pedido.class);

		    lineMapper.setLineTokenizer(tokenizer);
		    lineMapper.setFieldSetMapper(fieldSetMapper);

		    reader.setLineMapper(lineMapper);
		    return reader;
	}
}
