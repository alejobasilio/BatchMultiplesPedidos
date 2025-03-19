# Batch Multiples Pedidos

Batch Multiples Pedidos es un proyecto de Spring Batch que permite lanzar jobs para diferentes funcionalidades como crear archivos o insertar datos en la BBDD.

> [!IMPORTANT]
> ## Instalación
> 1. **Crear la base de datos**:
> * Abre MySQL Workbench y ejecuta el data.sql que se encuentra en la carpeta resources).
> 2. **Configurar la conexión a la base de datos**:
> * Abre el archivo `BatchConfig` en la carpeta `com.alejobasilio.batch_multiple_pedidos.config` y actualiza las propiedades del datasource de conexión a la base de datos con tus credenciales de MySQL:
> + `spring.datasource.url=jdbc:mysql://localhost:3306/prueba_tienda`
> + `spring.datasource.username=tu_usuario`
> + `spring.datasource.password=tu_contraseña`
> 3. **Instalar las dependencias**:
> * Ejecuta el comando `mvn clean install` en la terminal para instalar las dependencias del proyecto.
>
>   ## Requisitos
> El proyecto requiere los siguientes requisitos técnicos:
> * Java 11 o superior
> * Maven 3.6 o superior
> * Spring Boot 2.3 o superior

>[!NOTE]
>## Autores
> * Alejo Basilio Alfonso
