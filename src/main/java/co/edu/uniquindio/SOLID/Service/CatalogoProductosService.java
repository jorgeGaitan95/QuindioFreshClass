package co.edu.uniquindio.SOLID.Service;

import co.edu.uniquindio.SOLID.model.Minimercado;
import co.edu.uniquindio.SOLID.model.Producto;

public class CatalogoProductosService {

    public Producto buscarProducto(String sku) {
        for (Producto producto : Minimercado.getInstancia().getProductos()) {
            if (producto.getSku().equalsIgnoreCase(sku)) {
                return producto;
            }
        }
        return null;
    }
}
