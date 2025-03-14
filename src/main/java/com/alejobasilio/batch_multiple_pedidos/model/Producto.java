package com.alejobasilio.batch_multiple_pedidos.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Producto implements Serializable{

	private Long id;
	private String producto;
	private Double precio;
	private String moneda;
}
