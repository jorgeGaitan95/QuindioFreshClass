package co.edu.uniquindio.SOLID.Model;

import co.edu.uniquindio.SOLID.Model.Pago.MetodoPago;
import co.edu.uniquindio.SOLID.Model.Pago.PagoFactory;
import co.edu.uniquindio.SOLID.Model.Envio.*;
import co.edu.uniquindio.SOLID.Model.Notificacion.*;
import co.edu.uniquindio.SOLID.Model.Pedidos.ItemPedido;
import co.edu.uniquindio.SOLID.Model.Pedidos.Pedido;
import co.edu.uniquindio.SOLID.Model.Pedidos.PedidoBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que gestiona el minimercado (Singleton)
 * Contiene toda la lógica de negocio
 */
public class Minimercado {
    private List<Producto> productos;
    private List<Pedido> pedidos;
    private List<Cliente> clientes;
    private static Minimercado instancia;

    private Minimercado() {
        productos = new ArrayList<>();
        pedidos = new ArrayList<>();
        clientes = new ArrayList<>();
    }

    public static Minimercado getInstancia() {
        if (instancia == null) {
            instancia = new Minimercado();
        }
        return instancia;
    }

    public void addProducto(Producto producto) {
        productos.add(producto);
    }

    public void agregarProducto(Producto producto) {
        if (buscarProducto(producto.getSku()) == null) {
            productos.add(producto);
        }
    }

    public Producto buscarProducto(String sku) {
        for (Producto p : productos) {
            if (p.getSku().equals(sku)) {
                return p;
            }
        }
        return null;
    }

    public boolean actualizarProducto(String sku, String nuevoNombre, double nuevoPrecio) {
        Producto producto = buscarProducto(sku);
        if (producto != null) {
            producto.setNombre(nuevoNombre);
            producto.setPrecio(nuevoPrecio);
            return true;
        }
        return false;
    }

    public boolean eliminarProducto(String sku) {
        Producto producto = buscarProducto(sku);
        if (producto != null) {
            productos.remove(producto);
            return true;
        }
        return false;
    }

    public List<Producto> getProductos() {
        return productos;
    }
    
    public void addCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void agregarCliente(Cliente cliente) {
        if (buscarCliente(cliente.getCedula()) == null) {
            clientes.add(cliente);
        }
    }

    public Cliente buscarCliente(String cedula) {
        for (Cliente c : clientes) {
            if (c.getCedula().equals(cedula)) {
                return c;
            }
        }
        return null;
    }

    public boolean actualizarCliente(String cedula, String nuevoNombre, String nuevoCorreo, String nuevoTelefono) {
        Cliente cliente = buscarCliente(cedula);
        if (cliente != null) {
            cliente.setNombre(nuevoNombre);
            cliente.setCorreo(nuevoCorreo);
            cliente.setTelefono(nuevoTelefono);
            return true;
        }
        return false;
    }

    public boolean eliminarCliente(String cedula) {
        Cliente cliente = buscarCliente(cedula);
        if (cliente != null) {
            clientes.remove(cliente);
            return true;
        }
        return false;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public Pedido procesarPedido(Cliente cliente, List<ItemPedido> items, String direccionEnvio,
                                 String notas, String codigoDescuento,
                                 String tipoEnvio, String metodoPago, String tipoNotificacion) {

        String codigo = "PED-" + System.currentTimeMillis();
        
        // Construir pedido usando Builder (Patrón Builder)
        PedidoBuilder builder = new PedidoBuilder(codigo, cliente, items, direccionEnvio);
        
        if (notas != null && !notas.isEmpty()) {
            builder.withNotas(notas);
        }
        
        if (codigoDescuento != null && !codigoDescuento.isEmpty()) {
            builder.withCodigoDescuento(codigoDescuento);
        }
        
        Pedido pedido = builder.build();
        
        // Configurar opciones del pedido
        if (tipoEnvio != null) {
            pedido.setTipoEnvio(tipoEnvio);
        }
        if (metodoPago != null) {
            pedido.setMetodoPago(metodoPago);
        }
        if (tipoNotificacion != null) {
            pedido.setTipoNotificacion(tipoNotificacion);
        }
        
        // Procesar pago usando Factory (Patrón Factory)
        MetodoPago pago = PagoFactory.crearPago(metodoPago != null ? metodoPago : "PSE");
        boolean pagoExitoso = pago.procesarPago(pedido.calcularTotal());
        
        if (!pagoExitoso) {
            throw new RuntimeException("El pago no pudo ser procesado");
        }
        
        // Crear envío usando Factory (Patrón Factory)
        Envio envio = EnvioFactory.crearEnvio(tipoEnvio != null ? tipoEnvio : "ESTANDAR");
        double costoEnvio = envio.calcularCostoEnvio();
        
        // Enviar notificación usando Factory (Patrón Factory)
        Notificacion notificacion = NotificacionFactory.crearNotificacion(
            tipoNotificacion != null ? tipoNotificacion : "EMAIL"
        );
        notificacion.enviar(
            "Pedido " + codigo + " procesado exitosamente. Total: $" + pedido.calcularTotal()
        );
        
        // Agregar pedido a la lista
        pedidos.add(pedido);
        
        System.out.println("Pedido procesado: " + codigo + " - Total: $" + pedido.calcularTotal());
        
        return pedido;
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public Pedido buscarPedido(String codigo) {
        for (Pedido p : pedidos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }
}
