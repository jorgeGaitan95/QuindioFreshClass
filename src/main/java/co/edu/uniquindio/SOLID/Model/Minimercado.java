package co.edu.uniquindio.SOLID.model;

import java.util.ArrayList;
import java.util.List;

public class Minimercado {
    List<Producto> productos;
    List<Pedido> pedidos;
    List<Cliente> clientes;

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

    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }


    public void addCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
