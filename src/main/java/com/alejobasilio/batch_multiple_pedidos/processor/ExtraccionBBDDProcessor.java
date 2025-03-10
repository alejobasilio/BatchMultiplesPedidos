package com.alejobasilio.batch_multiple_pedidos.processor;

import org.springframework.batch.item.ItemProcessor;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;

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
