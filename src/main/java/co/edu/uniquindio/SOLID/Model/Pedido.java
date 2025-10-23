package co.edu.uniquindio.SOLID.Model;

import java.time.LocalDateTime;
import co.edu.uniquindio.SOLID.Model.Pedidos.*;
import java.util.List;

/**
 * Entidad Pedido con lógica de negocio
 */
public class Pedido {
    private String codigo;
    private LocalDateTime fechaCreacion;
    private Cliente cliente;
    private List<ItemPedido> items;
    private String direccionEnvio;
    private String notas;
    private String codigoDescuento;
    private String tipoEnvio; // ESTANDAR o EXPRESS
    private String metodoPago; // PSE o TARJETA_CREDITO
    private String tipoNotificacion; // EMAIL o SMS

    public Pedido(PedidoBuilder builder) {
        this.codigo = builder.codigo;
        this.fechaCreacion = builder.fechaCreacion;
        this.cliente = builder.cliente;
        this.items = builder.items;
        this.direccionEnvio = builder.direccionEnvio;
        this.notas = builder.notas;
        this.codigoDescuento = builder.codigoDescuento;
        this.tipoEnvio = "ESTANDAR"; // Por defecto
        this.metodoPago = "PSE"; // Por defecto
        this.tipoNotificacion = "EMAIL"; // Por defecto
    }

    // ========== LÓGICA DE NEGOCIO ==========
    
    /**
     * Calcula el subtotal de todos los items del pedido
     */
    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemPedido item : items) {
            subtotal += item.calcularSubtotal();
        }
        return subtotal;
    }

    /**
     * Calcula el costo de envío según el tipo
     */
    public double calcularCostoEnvio() {
        if (tipoEnvio != null && tipoEnvio.equals("EXPRESS")) {
            return 15000;
        } else {
            return 7000; // ESTANDAR
        }
    }

    /**
     * Calcula el total del pedido (subtotal + envío)
     */
    public double calcularTotal() {
        return calcularSubtotal() + calcularCostoEnvio();
    }

    // ========== GETTERS Y SETTERS ==========
    
    public String getCodigo() { return codigo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItems() { return items; }
    public String getDireccionEnvio() { return direccionEnvio; }
    public String getNotas() { return notas; }
    public String getCodigoDescuento() { return codigoDescuento; }
    public String getTipoEnvio() { return tipoEnvio; }
    public String getMetodoPago() { return metodoPago; }
    public String getTipoNotificacion() { return tipoNotificacion; }

    public void setTipoEnvio(String tipoEnvio) { this.tipoEnvio = tipoEnvio; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public void setTipoNotificacion(String tipoNotificacion) { this.tipoNotificacion = tipoNotificacion; }

    @Override
    public String toString() {
        return "Pedido{" +
                "codigo='" + codigo + '\'' +
                ", cliente=" + cliente.getNombre() +
                ", items=" + items.size() +
                ", total=" + calcularTotal() +
                '}';
    }
}
