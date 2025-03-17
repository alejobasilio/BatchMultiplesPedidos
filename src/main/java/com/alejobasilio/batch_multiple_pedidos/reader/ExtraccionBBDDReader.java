package com.alejobasilio.batch_multiple_pedidos.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;

/**
 * Clase que se utiliza para leer los pedidos desde la base de datos.
 * 
 * @author Alejo
 * @version 1.0
 * @since 1.0
 */
@Component
public class ExtraccionBBDDReader {

    /**
     * Fuente de datos que se utiliza para conectarse a la base de datos.
     */
	private DataSource dataSource;

	public ExtraccionBBDDReader(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
    /**
     * Método que crea un lector de pedidos que se utiliza para leer los pedidos desde la base de datos.
     * 
     * @return el lector de pedidos configurado.
     */
	@Bean
    public JdbcCursorItemReader<Pedido> reader() {
        return new JdbcCursorItemReaderBuilder<Pedido>()
                .dataSource(dataSource)
                .sql("SELECT * FROM pedidos")
                .rowMapper(new PedidoRowMapper())
                .name("extraccionBBDDReader")
                .build();
    }
    
	/**
     * Clase que se utiliza para mapear los resultados de la consulta a objetos de tipo Pedido.
     * 
     */
    public static class PedidoRowMapper implements RowMapper<Pedido> {
        @Override
        public Pedido mapRow(java.sql.ResultSet resultado, int rowNum) throws java.sql.SQLException {
        	Pedido pedido = new Pedido();
        	pedido.setId(resultado.getLong("id"));
        	pedido.setCliente(resultado.getString("cliente"));
        	pedido.setId_producto(resultado.getLong("id_producto"));
        	pedido.setImporte(resultado.getDouble("importe"));
        	pedido.setEstado(resultado.getBoolean("estado"));
            return pedido;
        }
    }
}
