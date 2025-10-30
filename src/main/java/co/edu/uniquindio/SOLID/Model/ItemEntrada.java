package co.edu.uniquindio.SOLID.Model;

public class ItemEntrada {
    private Producto producto;
    private int cantidad;

    public ItemEntrada(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
}


