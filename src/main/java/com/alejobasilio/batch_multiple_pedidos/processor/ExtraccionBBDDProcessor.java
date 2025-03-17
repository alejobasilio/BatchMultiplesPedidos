package com.alejobasilio.batch_multiple_pedidos.processor;

import org.springframework.batch.item.ItemProcessor;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;

/**
 * Procesador de pedidos que se utiliza para filtrar los pedidos que no están en estado "true".
 * 
 * @author Alejo
 * @version 1.0
 * @since 1.0
 */
public class ExtraccionBBDDProcessor implements ItemProcessor<Pedido,Pedido> {

	@Override
	public Pedido process(Pedido item) throws Exception {
		if(!item.isEstado()) {
			return item;
		}else {
			return null;
		}
		
	}

}
