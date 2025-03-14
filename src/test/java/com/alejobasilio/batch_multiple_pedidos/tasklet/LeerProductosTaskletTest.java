package com.alejobasilio.batch_multiple_pedidos.tasklet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.ClassPathResource;

import com.alejobasilio.batch_multiple_pedidos.model.Producto;

public class LeerProductosTaskletTest {

    private LeerProductosTasklet tasklet;
    private List<Producto> productosValidos;

    @BeforeEach
    public void setUp() {
        productosValidos = new ArrayList<>();
        tasklet = new LeerProductosTasklet(productosValidos);
    }

    @Test
    public void testExecute() throws Exception {
        // Crear un StepContribution y un ChunkContext
        StepContribution contribution = new StepContribution(new StepExecution("test", new JobExecution(1L)));
        ChunkContext chunkContext = new ChunkContext(new StepContext(new StepExecution("test", new JobExecution(1L))));

        // Llamar al método execute
        RepeatStatus status = tasklet.execute(contribution, chunkContext);

        // Verificar que se hayan leído los productos correctamente
        assertNotNull(productosValidos);
        assertEquals(20, productosValidos.size());

        // Verificar que los productos tengan los valores correctos
        for (Producto producto : productosValidos) {
            assertNotNull(producto);
            assertNotNull(producto.getId());
            assertNotNull(producto.getProducto());
            assertNotNull(producto.getPrecio());
            assertNotNull(producto.getMoneda());
        }
    }
}
