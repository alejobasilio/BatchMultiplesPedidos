package com.alejobasilio.batch_multiple_pedidos.writer;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;

/**
 * Clase que se utiliza para escribir los pedidos pendientes en un archivo CSV.
 * 
 * @author Alejo
 * @version 1.0
 * @since 1.0
 */
@Component
public class ExtraccionBBDDWriter extends FlatFileItemWriter<Pedido> {

	public ExtraccionBBDDWriter() {
		setResource(new FileSystemResource("src/main/resources/output/pedidos_pendientes.csv"));
		setAppendAllowed(false);

		DelimitedLineAggregator<Pedido> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(";");
		BeanWrapperFieldExtractor<Pedido> extractor = new BeanWrapperFieldExtractor<>();
		extractor.setNames(new String[] { "id", "cliente", "id_producto", "importe" });
		aggregator.setFieldExtractor(extractor);

		setLineAggregator(aggregator);

		setHeaderCallback(new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write("ID;Cliente;ID Producto;Importe");
			}
		});
	}
}
