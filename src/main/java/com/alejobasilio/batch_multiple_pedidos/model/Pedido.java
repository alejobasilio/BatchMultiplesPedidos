package com.alejobasilio.batch_multiple_pedidos.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa un pedido, con atributos para id, nombre del cliente, id_producto, importe, estado y valido.
 * Proporciona constructores y métodos getter y setter para acceder y modificar los atributos.
 * 
 * @author Alejo Basilio Alfonso
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pedido implements Serializable{

	private Long id;
	private String cliente;
	private Long id_producto;
	private Double importe;
	private boolean estado;
	private boolean valido;

}
