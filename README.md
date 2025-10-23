# Quindío Fresh - Sistema de Pedidos Online

## Descripción del Proyecto

"Quindío Fresh" es un mini-mercado online que conecta a los consumidores del Eje Cafetero con productos locales frescos como frutas, verduras y cafés especiales. Este proyecto implementa un sistema robusto, escalable y mantenible aplicando principios de buen diseño de software y patrones de diseño.

## Características Principales

### Requerimientos Funcionales Implementados

- **RF-01: Gestión de Catálogo de Productos** - Sistema completo de productos con SKU, nombre y precio
- **RF-02: Gestión de Clientes** - Registro y gestión de información de clientes
- **RF-03: Creación de Pedidos** - Sistema completo de creación de pedidos con todos los campos requeridos
- **RF-04: Procesamiento de Pagos** - Soporte para Tarjeta de Crédito y PSE
- **RF-05: Cálculo de Envíos** - Envío Estándar ($7.000) y Express ($15.000)
- **RF-06: Notificación al Cliente** - Notificaciones por SMS y Email

### Patrones de Diseño Implementados

#### 1. Builder Pattern
- **Clase**: `PedidoBuilder`
- **Propósito**: Construcción paso a paso de objetos Pedido complejos
- **Beneficios**: Evita constructores con muchos parámetros, construcción clara y validaciones

#### 2. Factory Method Pattern
- **Clases**: `PagoFactory`, `EnvioFactory`, `NotificacionFactory`
- **Propósito**: Creación desacoplada de objetos de pago, envío y notificación
- **Beneficios**: Fácil extensión para nuevos tipos sin modificar código existente

#### 3. Singleton Pattern
- **Clase**: `Minimercado`
- **Propósito**: Garantizar una única instancia del catálogo de productos y datos
- **Beneficios**: Acceso global controlado a los datos del sistema

#### 4. Facade Pattern
- **Clase**: `PedidoFacade`
- **Propósito**: Simplificar la interfaz para el procesamiento completo de pedidos
- **Beneficios**: Ocultar la complejidad del subsistema de pedidos

## Estructura del Proyecto

```
src/main/java/co/edu/uniquindio/SOLID/
├── App.java                          # Clase principal de la aplicación
├── Controlador/
│   ├── HolaMundoController.java      # Controlador de ejemplo
│   └── PedidoController.java         # Controlador principal del sistema
├── Model/
│   ├── Cliente.java                  # Entidad Cliente
│   ├── ItemPedido.java               # Entidad ItemPedido
│   ├── Minimercado.java              # Singleton para datos del sistema
│   ├── Pedido.java                   # Entidad Pedido
│   ├── PedidoBuilder.java            # Builder para Pedido
│   ├── Producto.java                 # Entidad Producto
│   └── DTO/
│       ├── ClienteDTO.java           # DTO para Cliente
│       ├── ItemPedidoDTO.java        # DTO para ItemPedido
│       ├── PedidoDTO.java            # DTO para Pedido
│       ├── ProductoDTO.java          # DTO para Producto
│       └── ResumenPedidoDTO.java     # DTO para resumen de pedido
├── Service/
│   ├── CatalogoProductosService.java # Servicio de catálogo
│   ├── ClienteService.java           # Servicio de clientes
│   ├── PedidoService.java            # Servicio de pedidos
│   ├── Envio/
│   │   ├── Envio.java                # Interfaz de envío
│   │   ├── EnvioEstandar.java        # Implementación envío estándar
│   │   ├── EnvioExpress.java         # Implementación envío express
│   │   └── EnvioFactory.java         # Factory para envíos
│   ├── Notificacion/
│   │   ├── Notificacion.java         # Interfaz de notificación
│   │   ├── NotificacionEmail.java    # Implementación email
│   │   ├── NotificacionSms.java      # Implementación SMS
│   │   └── NotificacionFactory.java  # Factory para notificaciones
│   ├── Pago/
│   │   ├── MetodoPago.java           # Interfaz de pago
│   │   ├── PagoPSE.java              # Implementación PSE
│   │   ├── PagoTarjetaCredito.java   # Implementación tarjeta
│   │   └── PagoFactory.java          # Factory para pagos
│   └── Fachadas/
│       └── PedidoFacade.java         # Fachada para procesamiento de pedidos
└── utils/
    ├── AppSetup.java                 # Configuración inicial de datos
    └── JFXPaths.java                 # Rutas de archivos FXML
```

## Tecnologías Utilizadas

- **Java 11+**
- **JavaFX** - Para la interfaz gráfica de usuario
- **Maven** - Para la gestión de dependencias
- **FXML** - Para el diseño de la interfaz

## Cómo Ejecutar el Proyecto

### Prerrequisitos
- Java 11 o superior
- Maven 3.6 o superior

### Pasos para Ejecutar

1. **Clonar o descargar el proyecto**
2. **Navegar al directorio del proyecto**
   ```bash
   cd Proyecto1
   ```
3. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```
4. **Ejecutar la aplicación**
   ```bash
   mvn javafx:run
   ```

### Alternativa con IDE
1. Importar el proyecto en tu IDE favorito (IntelliJ IDEA, Eclipse, VS Code)
2. Ejecutar la clase `App.java` como aplicación Java

## Funcionalidades de la Interfaz

### Pantalla Principal
- **Selección de Cliente**: ComboBox con clientes predefinidos
- **Agregar Productos**: Selección de productos con cantidad
- **Tabla de Items**: Visualización de productos agregados al pedido
- **Información Adicional**: Dirección de envío, notas y código de descuento
- **Configuración**: Método de pago, tipo de envío y notificación
- **Resumen**: Cálculo automático de subtotal, envío y total
- **Procesamiento**: Botón para procesar el pedido completo

### Datos de Prueba Incluidos

#### Productos
- Café Quindío Premium ($25,000)
- Banano Orgánico ($5,000)
- Aguacate Hass ($8,000)
- Plátano Verde ($3,000)
- Tomate Cherry ($12,000)
- Lechuga Hidropónica ($4,000)
- Zanahoria Orgánica ($6,000)
- Cebolla Cabezona ($7,000)

#### Clientes
- Julian (109314240)
- Andrea (1093)
- Pablo (1094)
- María (1095)
- Carlos (1096)
- Laura (1097)

## Extensibilidad del Sistema

El sistema está diseñado para ser fácilmente extensible:

### Agregar Nuevos Métodos de Pago
1. Crear nueva clase que implemente `MetodoPago`
2. Agregar caso en `PagoFactory`
3. Actualizar la interfaz para incluir la nueva opción

### Agregar Nuevos Tipos de Envío
1. Crear nueva clase que implemente `Envio`
2. Agregar caso en `EnvioFactory`
3. Actualizar la interfaz para incluir la nueva opción

### Agregar Nuevos Canales de Notificación
1. Crear nueva clase que implemente `Notificacion`
2. Agregar caso en `NotificacionFactory`
3. Actualizar la interfaz para incluir la nueva opción

## Principios SOLID Aplicados

- **Single Responsibility**: Cada clase tiene una responsabilidad específica
- **Open/Closed**: El sistema está abierto para extensión pero cerrado para modificación
- **Liskov Substitution**: Las implementaciones pueden sustituirse sin afectar el funcionamiento
- **Interface Segregation**: Interfaces específicas y cohesivas
- **Dependency Inversion**: Dependencias hacia abstracciones, no implementaciones concretas

## Contribuciones

Este proyecto fue desarrollado como parte de un taller práctico sobre patrones de diseño en el curso de Programación II de la Universidad del Quindío.

## Licencia

Este proyecto es de uso educativo y académico.

