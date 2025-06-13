#  Sistema de Gestión de Productos y Carrito de Compras

Este proyecto es una aplicación Java desarrollada para gestionar productos y simular un carrito de compras utilizando el paradigma de Programación Orientada a Objetos (POO). Se implementa una estructura modelo-vista-controlador (MVC) con persistencia en memoria.

---

##  Tabla de Contenidos

- [Características](#características)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Tecnologías](#tecnologías)
- [Clases Principales](#clases-principales)
- [Instrucciones de Uso](#instrucciones-de-uso)
- [Captura de Consola](#captura-de-consola)

---

## Características

- Crear, actualizar y eliminar productos.
- Agregar productos al carrito.
- Calcular total con y sin descuento.
- Impresión en consola del producto guardado.
- Implementación en capas: DAO, Service, Controller, View.

---

##  Estructura del Proyecto

```text
ec.edu.ups.poo
├── dao
│   ├── ProductoDAO.java
│   └── ProductoDAOMemoria.java
├── models
│   ├── Producto.java
│   └── ItemCarrito.java
├── service
│   └── CarritoService.java (interfaz)
├── controller
│   └── ProductoController.java
├── view
│   └── ProductoView.java
└── Main.java
