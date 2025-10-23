# Arquitectura del Sistema Quindío Fresh

## Diagrama de Clases Principal

```
┌─────────────────────────────────────────────────────────────────┐
│                        PEDIDO FACADE                           │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ + procesarPedidoCompleto()                             │   │
│  │ + crearPedido()                                        │   │
│  │ + obtenerTodosLosPedidos()                             │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                      PEDIDO BUILDER                            │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ + conCodigo()                                          │   │
│  │ + conCliente()                                         │   │
│  │ + conItem()                                            │   │
│  │ + conDireccionEnvio()                                  │   │
│  │ + conNotas()                                           │   │
│  │ + conCodigoDescuento()                                 │   │
│  │ + build()                                              │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                         PEDIDO                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ - codigo: String                                       │   │
│  │ - fechaCreacion: LocalDateTime                          │   │
│  │ - cliente: Cliente                                      │   │
│  │ - items: List<ItemPedido>                               │   │
│  │ - direccionEnvio: String                                │   │
│  │ - notas: String                                         │   │
│  │ - codigoDescuento: String                               │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

## Patrones de Diseño Implementados

### 1. Builder Pattern - PedidoBuilder
```
PedidoBuilder
├── conCodigo(String)
├── conCliente(Cliente)
├── conItem(ItemPedido)
├── conItems(List<ItemPedido>)
├── conDireccionEnvio(String)
├── conNotas(String)
├── conCodigoDescuento(String)
└── build() → Pedido
```

### 2. Factory Method Pattern

#### PagoFactory
```
PagoFactory
└── crearPago(String tipo) → MetodoPago
    ├── "TARJETA" → PagoTarjetaCredito
    └── "PSE" → PagoPSE
```

#### EnvioFactory
```
EnvioFactory
└── crearEnvio(String tipo) → Envio
    ├── "ESTANDAR" → EnvioEstandar
    └── "EXPRESS" → EnvioExpress
```

#### NotificacionFactory
```
NotificacionFactory
└── crearNotificacion(String tipo) → Notificacion
    ├── "EMAIL" → NotificacionEmail
    └── "SMS" → NotificacionSms
```

### 3. Singleton Pattern - Minimercado
```
Minimercado
├── - instancia: Minimercado (static)
├── - productos: List<Producto>
├── - clientes: List<Cliente>
├── - pedidos: List<Pedido>
├── + getInstancia() → Minimercado
├── + addProducto(Producto)
├── + addCliente(Cliente)
└── + addPedido(Pedido)
```

### 4. Facade Pattern - PedidoFacade
```
PedidoFacade
├── - pedidoService: PedidoService
├── - catalogoProductosService: CatalogoProductosService
├── - clienteService: ClienteService
├── + procesarPedidoCompleto() → String
├── + crearPedido() → Pedido
├── + obtenerTodosLosPedidos() → List<Pedido>
└── + buscarPedido(String) → Pedido
```

## Flujo de Procesamiento de Pedidos

```
1. Cliente selecciona productos y configura pedido
   ↓
2. PedidoController crea PedidoDTO
   ↓
3. PedidoFacade.procesarPedidoCompleto()
   ↓
4. PedidoBuilder construye objeto Pedido
   ↓
5. EnvioFactory crea objeto Envio
   ↓
6. PagoFactory crea objeto MetodoPago
   ↓
7. NotificacionFactory crea objeto Notificacion
   ↓
8. Se procesa pago, se calcula envío, se envía notificación
   ↓
9. Pedido se guarda en Minimercado (Singleton)
   ↓
10. Se retorna resultado al usuario
```

## Estructura de Capas

```
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE PRESENTACIÓN                    │
│  ┌─────────────────┐  ┌─────────────────┐                 │
│  │ PedidoController│  │ pedidoView.fxml │                 │
│  └─────────────────┘  └─────────────────┘                 │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE SERVICIOS                       │
│  ┌─────────────────┐  ┌─────────────────┐                 │
│  │ PedidoFacade    │  │ PedidoService   │                 │
│  │ CatalogoService │  │ ClienteService  │                 │
│  └─────────────────┘  └─────────────────┘                 │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE MODELO                          │
│  ┌─────────────────┐  ┌─────────────────┐                 │
│  │ Pedido          │  │ Cliente         │                 │
│  │ Producto        │  │ ItemPedido      │                 │
│  │ PedidoBuilder   │  │ Minimercado     │                 │
│  └─────────────────┘  └─────────────────┘                 │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE DTOs                            │
│  ┌─────────────────┐  ┌─────────────────┐                 │
│  │ PedidoDTO       │  │ ClienteDTO      │                 │
│  │ ItemPedidoDTO   │  │ ProductoDTO     │                 │
│  │ ResumenPedidoDTO│  │                 │                 │
│  └─────────────────┘  └─────────────────┘                 │
└─────────────────────────────────────────────────────────────┘
```

## Principios SOLID Aplicados

### Single Responsibility Principle (SRP)
- Cada clase tiene una responsabilidad específica
- PedidoBuilder solo se encarga de construir pedidos
- PagoFactory solo se encarga de crear métodos de pago
- Cada servicio maneja una entidad específica

### Open/Closed Principle (OCP)
- El sistema está abierto para extensión pero cerrado para modificación
- Para agregar nuevos métodos de pago, solo se necesita:
  - Crear nueva clase que implemente MetodoPago
  - Agregar caso en PagoFactory
  - No modificar código existente

### Liskov Substitution Principle (LSP)
- Todas las implementaciones de MetodoPago son intercambiables
- Todas las implementaciones de Envio son intercambiables
- Todas las implementaciones de Notificacion son intercambiables

### Interface Segregation Principle (ISP)
- Interfaces específicas y cohesivas
- MetodoPago solo tiene métodos relacionados con pagos
- Envio solo tiene métodos relacionados con envíos
- Notificacion solo tiene métodos relacionados con notificaciones

### Dependency Inversion Principle (DIP)
- Las clases de alto nivel no dependen de clases de bajo nivel
- Ambas dependen de abstracciones (interfaces)
- PedidoFacade depende de interfaces, no de implementaciones concretas

## Extensibilidad

El sistema está diseñado para ser fácilmente extensible:

### Agregar Nuevo Método de Pago
1. Crear clase que implemente `MetodoPago`
2. Agregar caso en `PagoFactory.crearPago()`
3. Actualizar interfaz de usuario

### Agregar Nuevo Tipo de Envío
1. Crear clase que implemente `Envio`
2. Agregar caso en `EnvioFactory.crearEnvio()`
3. Actualizar interfaz de usuario

### Agregar Nuevo Canal de Notificación
1. Crear clase que implemente `Notificacion`
2. Agregar caso en `NotificacionFactory.crearNotificacion()`
3. Actualizar interfaz de usuario

## Beneficios de la Arquitectura

1. **Mantenibilidad**: Código organizado y fácil de mantener
2. **Extensibilidad**: Fácil agregar nuevas funcionalidades
3. **Testabilidad**: Cada componente puede ser probado independientemente
4. **Reutilización**: Los componentes pueden ser reutilizados
5. **Flexibilidad**: Fácil cambiar implementaciones sin afectar el resto del sistema
6. **Separación de Responsabilidades**: Cada capa tiene su propósito específico

