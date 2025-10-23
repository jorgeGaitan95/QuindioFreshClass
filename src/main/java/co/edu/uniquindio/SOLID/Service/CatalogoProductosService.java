package co.edu.uniquindio.SOLID.Service;

import co.edu.uniquindio.SOLID.Model.Minimercado;
import co.edu.uniquindio.SOLID.Model.Producto;

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
