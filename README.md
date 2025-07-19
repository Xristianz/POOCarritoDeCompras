# Proyecto Carrito de Compras - Programación Orientada a Objetos

Este proyecto es una aplicación de consola desarrollada en Java como parte del curso de Programación Orientada a Objetos. El objetivo principal es demostrar la aplicación de los principios de la POO y patrones de diseño de software en la construcción de un sistema funcional para la gestión de un carrito de compras.

## Principios y Diseño

El núcleo del proyecto se fundamenta en buenas prácticas de diseño de software para garantizar que el código sea mantenible, escalable y fácil de entender.

### Programación Orientada a Objetos (POO)

La aplicación está rigurosamente modelada siguiendo los pilares de la POO:
* **Encapsulamiento:** Cada clase protege su estado interno y solo expone las operaciones necesarias a través de métodos públicos, ocultando la complejidad interna.
* **Abstracción:** Se utilizan clases e interfaces abstractas para definir contratos y comportamientos comunes, como en el caso de los DAO.
* **Herencia y Polimorfismo:** Se aplican para crear jerarquías de clases lógicas y permitir que los objetos de diferentes clases respondan a la misma interfaz de manera distinta.

### Patrón de Diseño DAO (Data Access Object)

Se ha implementado el patrón de diseño **DAO** para gestionar la persistencia de los datos. Este patrón es crucial porque desacopla completamente la lógica de negocio de la lógica de acceso a datos.

* **Flexibilidad:** Gracias al DAO, el método de almacenamiento (memoria, archivos de texto, base de datos) puede ser intercambiado fácilmente sin necesidad de modificar el resto de la aplicación.
* **Separación de Responsabilidades:** La capa de negocio no necesita saber *cómo* se guardan los datos, solo necesita pedirle al DAO que los guarde o los recupere.

## Funcionalidades Implementadas

El sistema cuenta con las siguientes funcionalidades clave:

* **Gestión de Productos:**
    * Crear nuevos productos con atributos como código, nombre y precio.
    * Leer (buscar) productos existentes en el sistema.
    * Actualizar la información de los productos.
    * Eliminar productos del catálogo.

* **Gestión de Carritos de Compra:**
    * Creación de múltiples carritos de compra.
    * Asociación de productos a un carrito específico.
    * Capacidad para agregar y eliminar productos del carrito.

* **Cálculos y Operaciones:**
    * Cálculo automático del subtotal y total del carrito de compras.
    * Manejo de la lógica para el proceso de "checkout" o finalización de la compra.

## Manejo de Errores y Excepciones

Para garantizar la robustez y una experiencia de usuario fluida, el sistema incorpora un manejo de errores proactivo:
* **Excepciones Personalizadas:** Se han creado excepciones específicas para situaciones de negocio, como `ProductoNoEncontradoException` o `StockInsuficienteException`. Esto permite manejar los errores de una manera más clara y específica en lugar de depender de excepciones genéricas.
* **Validación de Entradas:** Toda la información ingresada por el usuario es validada para prevenir errores comunes, como formatos de datos incorrectos (ej: ingresar texto en un campo numérico) o valores fuera de los rangos permitidos.
* **Bloques `try-catch`:** Se utilizan estratégicamente para capturar y gestionar excepciones en tiempo de ejecución, evitando que la aplicación se cierre inesperadamente y proporcionando mensajes de error claros al usuario.

## Documentación y Distribución

### Documentación con Javadoc

Todo el código fuente ha sido documentado siguiendo el estándar **Javadoc**. Cada clase, método y atributo público cuenta con una descripción detallada de su propósito, parámetros y valores de retorno. Esta documentación es fundamental para el mantenimiento del código y facilita la colaboración, ya que permite a otros desarrolladores entender rápidamente el funcionamiento del sistema.

La documentación completa puede ser generada a partir de los comentarios del código fuente.

### Archivo Ejecutable `.jar`

El proyecto ha sido compilado y empaquetado en un archivo **`.jar` ejecutable**. Esto permite que la aplicación sea distribuida y ejecutada en cualquier sistema que tenga la Máquina Virtual de Java (JVM) instalada, sin necesidad de compilar el código fuente.

Para ejecutar la aplicación, simplemente se debe utilizar el siguiente comando en la terminal:

## Estructura del Proyecto

El proyecto está organizado en paquetes para seguir una arquitectura limpia y ordenada:

* `ec.edu.ups.poo.models`: Contiene las clases que modelan las entidades del negocio (Ej: `Producto`, `Carrito`, `ItemCarrito`).
* `ec.edu.ups.poo.dao`: Contiene las interfaces DAO y sus implementaciones concretas para el acceso a datos.
* `ec.edu.ups.poo.controlador`: Contiene la lógica de negocio y coordina las interacciones entre la vista y los modelos.
* `ec.edu.ups.poo.vista`: Responsable de interactuar con el usuario a través de la consola.

## Tecnologías Utilizadas

* **Lenguaje:** Java
* **Control de Versiones:** Git y GitHub

## Autor

* **Cristian Moscoso **
