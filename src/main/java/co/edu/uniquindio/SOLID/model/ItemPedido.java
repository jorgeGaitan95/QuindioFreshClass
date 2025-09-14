package co.edu.uniquindio.SOLID.model;

public class ItemPedido {
    private Producto producto;
    private int cantidad;

    public ItemPedido(Producto producto, int cantidad) {
        if (producto == null) throw new IllegalArgumentException("El producto no puede ser nulo");
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return producto.getSku() + " x" + cantidad;
    }
}