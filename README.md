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
>## Enunciado
>Ejercicios Spring Batch múltiple
Se requiere elaborar un proceso para la validación de pedidos según algunas condiciones. Se debe realizar un proyecto Spring Batch que realice lo siguiente:
1.	Extracción de BBDD
Se deben leer los pedidos de BBDD, se filtrarán según su estado y se escribirá un fichero con los pedidos que cumplan dicha condición.

a.	Leemos la tabla “pedidos”.
b.	Procesamos únicamente los pedidos pendientes (FALSE).
c.	Almacenamos la información en “pedidos_pendientes.csv”

2.	Validación de los pedidos
Se deben validar los pedidos, para ello filtraremos los productos de los pedidos con los productos válidos. Si el pedido contiene un producto válido entonces el pedido lo será también.


a.	Leemos los productos válidos del fichero “productos_validos.dat” y lo almacenamos en memoria.(Tasklet)
b.	Leemos los pedidos pendientes del fichero “pedidos_pendientes.csv”.
c.	Filtramos qué pedidos son válidos y cuáles no.
d.	Almacenamos la información en “pedidos_validados.json”.

3.	Escritura de los pedidos validados en BBDD
Se debe escribir en BBDD los pedidos válidos.

a.	Leemos el fichero “pedidos_validados.json”
b.	Filtramos los pedidos que son válidos.
c.	Almacenamos la información en la tabla “pedidos_validos”.

Información adicional
La estructura de los distintos ficheros/tablas es la siguiente:
BBDD:
	“pedidos”: 			id - cliente - id_producto - importe - estado 
	“pedidos_validos”: 		id - cliente - id_producto - importe

Ficheros:
	“pedidos_pendientes.csv”:	id;cliente;id_producto;importe
	“productos_validos.dat”:	id,producto

 pedidos_validados.json: ![image](https://github.com/user-attachments/assets/a67e9ddf-5fae-4930-9318-700259314331)

