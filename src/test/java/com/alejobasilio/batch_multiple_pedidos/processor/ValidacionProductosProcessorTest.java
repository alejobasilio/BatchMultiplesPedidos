package com.alejobasilio.batch_multiple_pedidos.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;
import com.alejobasilio.batch_multiple_pedidos.model.Producto;

public class ValidacionProductosProcessorTest {

    private ValidacionProductosProcessor processor;
    private List<Producto> productosValidos;

    @BeforeEach
    public void setUp() {
        productosValidos = new ArrayList<>();
        processor = new ValidacionProductosProcessor(productosValidos);
    }

    @Test
    public void testProcess_PedidoNoValido() throws Exception {
        
        Pedido pedido = new Pedido();
        pedido.setId_producto(1L);

        
        Pedido resultado = processor.process(pedido);

        
        assertFalse(resultado.isValido());
    }

    @Test
    public void testProcess_PedidoValido() throws Exception {
        
        Producto producto = new Producto();
        producto.setId(1L);
        productosValidos.add(producto);

        
        Pedido pedido = new Pedido();
        pedido.setId_producto(1L);

        
        Pedido resultado = processor.process(pedido);

        
        assertTrue(resultado.isValido());
    }

    @Test
    public void testProcess_PedidoNoValidoConProductosValidos() throws Exception {
        
        Producto producto = new Producto();
        producto.setId(1L);
        productosValidos.add(producto);

        
        Pedido pedido = new Pedido();
        pedido.setId_producto(2L);

        
        Pedido resultado = processor.process(pedido);

        
        assertFalse(resultado.isValido());
    }
}
