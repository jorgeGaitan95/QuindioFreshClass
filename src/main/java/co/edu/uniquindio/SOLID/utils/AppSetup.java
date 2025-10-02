package co.edu.uniquindio.SOLID.utils;

import co.edu.uniquindio.SOLID.Model.Minimercado;
import co.edu.uniquindio.SOLID.Model.Producto;

public class AppSetup {

    public AppSetup(){
        inicializarCatalogoProductos();
    }

    public void inicializarCatalogoProductos (){
        Minimercado minimercado = Minimercado.getInstancia();
        minimercado.addProducto(new Producto("SKU-01", "Café Quindío", 25000));
        minimercado.addProducto(new Producto("SKU-02", "Banano", 5000));
        minimercado.addProducto(new Producto("SKU-03", "Aguacate", 8000));
    }

}
