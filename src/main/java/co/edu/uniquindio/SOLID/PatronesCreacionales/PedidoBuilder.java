package co.edu.uniquindio.SOLID.PatronesCreacionales;

import co.edu.uniquindio.SOLID.model.Cliente;
import co.edu.uniquindio.SOLID.model.ItemPedido;
import co.edu.uniquindio.SOLID.model.Pedidos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public  class PedidoBuilder {

    private String codigo;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private Cliente cliente;
    private List<ItemPedido> items = new ArrayList<>();
    private String direccionEnvio;
    private String notas;
    private String codigoDescuento;

    public PedidoBuilder codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public PedidoBuilder Cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public PedidoBuilder items(List<ItemPedido> items) {
        this.items = items;
        return this;
    }

    public PedidoBuilder direccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
        return this;
    }

    public PedidoBuilder notas(String notas) {
        this.notas = notas;
        return this;
    }

    public PedidoBuilder codigoDescuento(String codigoDescuento) {
        this.codigoDescuento = codigoDescuento;
        return this;
    }

    public Pedidos build() {
        return new Pedidos(codigo, fechaCreacion,  cliente, items, direccionEnvio, notas, codigoDescuento);
    }
}

