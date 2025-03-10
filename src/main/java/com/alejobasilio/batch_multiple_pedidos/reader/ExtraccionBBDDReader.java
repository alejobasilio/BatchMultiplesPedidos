package com.alejobasilio.batch_multiple_pedidos.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;

@Component
public class ExtraccionBBDDReader {

	private DataSource dataSource;

	@Autowired
	public ExtraccionBBDDReader(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
    public JdbcCursorItemReader<Pedido> reader() {
        return new JdbcCursorItemReaderBuilder<Pedido>()
                .dataSource(dataSource)
                .sql("SELECT * FROM pedidos")
                .rowMapper(new PedidoRowMapper())
                .build();
    }
    
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
