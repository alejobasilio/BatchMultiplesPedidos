package com.alejobasilio.batch_multiple_pedidos.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;

import com.alejobasilio.batch_multiple_pedidos.model.Pedido;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ValidacionProductosWriterTest {

    private ValidacionProductosWriter writer;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        writer = new ValidacionProductosWriter();
        mapper = new ObjectMapper();
    }

    @AfterEach
    public void tearDown() {
        
        File file = new File("src/test/resources/pedidos_validados.json");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testEscribirPedidosValidadosWriter_PedidoUnico() throws Exception {
        
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        
        writer.escribirPedidosValidadosWriter().write(new Chunk<>(List.of(pedido)));

        
        File file = new File("src/test/resources/pedidos_validados.json");
        assertTrue(file.exists());

        
        List<Pedido> pedidos = mapper.readValue(file, new TypeReference<List<Pedido>>() {});

        
        assertEquals(1, pedidos.size());
        assertEquals(1L, pedidos.get(0).getId());
    }

    @Test
    public void testEscribirPedidosValidadosWriter_PedidosMultiples() throws Exception {
        
        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);
        Pedido pedido2 = new Pedido();
        pedido2.setId(2L);

        
        writer.escribirPedidosValidadosWriter().write(new Chunk<>(List.of(pedido1, pedido2)));

        
        File file = new File("src/test/resources/pedidos_validados.json");
        assertTrue(file.exists());

       
        List<Pedido> pedidos = mapper.readValue(file, new TypeReference<List<Pedido>>() {});

        
        assertEquals(2, pedidos.size());
        assertEquals(1L, pedidos.get(0).getId());
        assertEquals(2L, pedidos.get(1).getId());
    }
}
