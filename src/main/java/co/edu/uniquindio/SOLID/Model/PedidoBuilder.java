package co.edu.uniquindio.SOLID.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public  class PedidoBuilder {

    String codigo;
    LocalDateTime fechaCreacion = LocalDateTime.now();
    Cliente cliente;
    List<ItemPedido> items = new ArrayList<>();
    String direccionEnvio;
    String notas;
    String codigoDescuento;

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


