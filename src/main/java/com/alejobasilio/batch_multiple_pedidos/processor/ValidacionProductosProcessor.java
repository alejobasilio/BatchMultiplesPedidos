package com.alejobasilio.batch_multiple_pedidos.processor;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;
import com.alejobasilio.batch_multiple_pedidos.model.Producto;

/**
 * Procesador de pedidos que se utiliza para filtrar los pedidos que tienen un producto valido.
 * 
 * @author Alejo
 * @version 1.0
 * @since 1.0
 */
public class ValidacionProductosProcessor implements ItemProcessor<Pedido, Pedido> {

	private final List<Producto> productosValidos;

	public ValidacionProductosProcessor(List<Producto> productosValidos) {
		this.productosValidos = productosValidos;
	}

	@Override
	public Pedido process(Pedido item) throws Exception {
		item.setValido(false);
		for (Producto producto : productosValidos) {
			if (item.getId_producto().equals(producto.getId())) {
				item.setValido(true);
			}
		}
		return item;
	}

}
