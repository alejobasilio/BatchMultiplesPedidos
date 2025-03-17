package com.alejobasilio.batch_multiple_pedidos.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa un producto, con atributos para id, nombre del producto, precio y moneda.
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
public class Producto implements Serializable{

	private Long id;
	private String producto;
	private Double precio;
	private String moneda;
}
