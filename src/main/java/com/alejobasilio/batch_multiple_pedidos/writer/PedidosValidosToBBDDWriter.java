package com.alejobasilio.batch_multiple_pedidos.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;

/**
 * Clase que se utiliza para escribir los pedidos válidos en la base de datos.
 * @author Alejo
 * @version 1.0
 * @since 1.0
 */
@Component
public class PedidosValidosToBBDDWriter {

	private final DataSource dataSource;

	public PedidosValidosToBBDDWriter(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public JdbcBatchItemWriter<Pedido> writer() {
		
		JdbcBatchItemWriter<Pedido> writer = new JdbcBatchItemWriter<>();
		writer.setItemPreparedStatementSetter(new CustomItemPreparedStatementSetter());
		writer.setSql("INSERT INTO pedidos_validos (id, cliente, id_producto, importe) VALUES (?, ?, ?, ?)");
		writer.setDataSource(dataSource);
	    return writer;
	}
	
	/**
     * Clase interna que se utiliza para establecer los valores de los parámetros de la consulta SQL.
     */
	public class CustomItemPreparedStatementSetter implements ItemPreparedStatementSetter<Pedido>{

		@Override
		public void setValues(Pedido item, PreparedStatement ps) throws SQLException {
			ps.setLong(1, item.getId());
	        ps.setString(2, item.getCliente());
	        ps.setLong(3, item.getId_producto());
	        ps.setDouble(4, item.getImporte());
			
		}
	}
}
