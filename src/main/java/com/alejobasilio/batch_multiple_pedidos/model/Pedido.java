package com.alejobasilio.batch_multiple_pedidos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pedido {

	private Long id;
	private String cliente;
	private Long id_producto;
	private Double importe;
	private boolean estado;

}
