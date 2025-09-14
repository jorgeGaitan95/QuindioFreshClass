package co.edu.uniquindio.SOLID.model;


import co.edu.uniquindio.SOLID.PatronesCreacionales.PedidoBuilder;
import co.edu.uniquindio.SOLID.Service.Envio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedidos {
    private String codigo;
    private LocalDateTime fechaCreacion;
    private Cliente cliente;
    private List<ItemPedido> items;
    private String direccionEnvio;
    private String notas;
    private String codigoDescuento;

    public Pedidos(String codigo, LocalDateTime fechaCreacion, Cliente cliente,
                   List<ItemPedido> items, String direccionEnvio, String notas, String codigoDescuento) {
        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
        this.items = (items != null) ? items : new ArrayList<>();
        this.direccionEnvio = direccionEnvio;
        this.notas = notas;
        this.codigoDescuento = codigoDescuento;
    }

    public static PedidoBuilder builder() { return new PedidoBuilder(); }

    public String getCodigo() {return codigo;}
    public LocalDateTime getFechaCreacion() {return fechaCreacion;}
    public Cliente getCliente() {return cliente;}
    public List<ItemPedido> getItems() {return items;}
    public String getDireccionEnvio() {return direccionEnvio;}
    public String getNotas() {return notas;}
    public String getCodigoDescuento() {return codigoDescuento;}

    public double calcularSubtotal() {
       return items.stream().mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad()).sum();
    }
    public double calcularTotal(Envio envio) {
        return calcularSubtotal() + envio.calcularCostoEnvio();
    }

    @Override
    public String toString() {
        return "Pedidos{" +
                "codigo='" + codigo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", cliente=" + cliente +
                ", items=" + items +
                ", direccionEnvio='" + direccionEnvio + '\'' +
                ", notas='" + notas + '\'' +
                ", codigoDescuento='" + codigoDescuento + '\'' +
                '}';
    }

}
