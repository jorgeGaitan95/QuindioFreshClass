package co.edu.uniquindio.SOLID.Model.Pedidos;

import co.edu.uniquindio.SOLID.Model.Cliente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public  class PedidoBuilder {

    public String codigo;
    public LocalDateTime fechaCreacion = LocalDateTime.now();
    public Cliente cliente;
    public List<ItemPedido> items = new ArrayList<>();
    public String direccionEnvio;
    public String notas;
    public String codigoDescuento;

    public PedidoBuilder (String codigo, Cliente cliente, List<ItemPedido> items, String direccionEnvio) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.items = items;
        this.direccionEnvio = direccionEnvio;
    }

    public PedidoBuilder withNotas(String notas) {
        this.notas = notas;
        return this;
    }

    public PedidoBuilder withCodigoDescuento(String codigoDescuento) {
        this.codigoDescuento = codigoDescuento;
        return this;
    }

    public Pedido build() {
        return new Pedido(this);
    }
}


