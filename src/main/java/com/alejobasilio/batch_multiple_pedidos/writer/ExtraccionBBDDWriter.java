package com.alejobasilio.batch_multiple_pedidos.writer;


import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;

@Component
public class ExtraccionBBDDWriter extends FlatFileItemWriter<Pedido>{

	public ExtraccionBBDDWriter() {
		setResource(new FileSystemResource("pedidos_pendientes.csv"));
		setAppendAllowed(false);
		
		DelimitedLineAggregator<Pedido> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(";");
		BeanWrapperFieldExtractor<Pedido> extractor = new BeanWrapperFieldExtractor<>();
		extractor.setNames(new String[] {"id", "cliente", "id_producto", "importe"});
		aggregator.setFieldExtractor(extractor);
		
		setLineAggregator(aggregator);
	}
}
