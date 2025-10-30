package co.edu.uniquindio.SOLID.Model;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private String codigo;
    private LocalDateTime fechaCreacion;
    private Cliente cliente;
    private List<ItemPedido> items;
    private String direccionEnvio;
    private String notas;
    private String codigoDescuento;

    public Pedido(PedidoBuilder builder) {
        this.codigo = builder.codigo;
        this.fechaCreacion = builder.fechaCreacion;
        this.cliente = builder.cliente;
        this.items = builder.items;
        this.direccionEnvio = builder.direccionEnvio;
        this.notas = builder.notas;
        this.codigoDescuento = builder.codigoDescuento;
    }


    public String getCodigo() {return codigo;}
    public LocalDateTime getFechaCreacion() {return fechaCreacion;}
    public Cliente getCliente() {return cliente;}
    public List<ItemPedido> getItems() {return items;}
    public String getDireccionEnvio() {return direccionEnvio;}
    public String getNotas() {return notas;}
    public String getCodigoDescuento() {return codigoDescuento;}
}
