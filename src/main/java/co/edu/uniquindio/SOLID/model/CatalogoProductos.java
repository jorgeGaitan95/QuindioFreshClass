package co.edu.uniquindio.SOLID.model;

import java.util.ArrayList;
import java.util.List;

public class CatalogoProductos {
    private static CatalogoProductos instancia;
    private List<Producto> productos;

    private CatalogoProductos() {
        // Inicializar la lista de productos
        productos = new ArrayList<>();
    }

    public static CatalogoProductos getInstancia() {
        if (instancia == null) {
            instancia = new CatalogoProductos();
        }
        return instancia;
    }

    public void agregarProducto(Producto producto) {
        if (producto == null) throw new IllegalArgumentException("El producto no puede ser nulo");
        productos.add(producto);
    }

    public Producto buscarProducto(String sku) {
        for (Producto producto : productos) {
            if (producto.getSku().equalsIgnoreCase(sku)) {
                return producto;
            }
        }
        return null;
    }
    public List<Producto> listarProductos() {
        return new ArrayList<>(productos);
    }
    public void mostrarCatalogo() {
        System.out.println("Catálogo de Productos:");
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }
}


