package co.edu.uniquindio.SOLID.utils;

import co.edu.uniquindio.SOLID.model.Cliente;
import co.edu.uniquindio.SOLID.model.Minimercado;
import co.edu.uniquindio.SOLID.model.Producto;

public class AppSetup {

    public AppSetup(){
        inicializarCatalogoProductos();
        inicializarClientes();
    }

    public void inicializarCatalogoProductos (){
        Minimercado minimercado = Minimercado.getInstancia();
        minimercado.addProducto(new Producto("SKU-01", "Café Quindío", 25000));
        minimercado.addProducto(new Producto("SKU-02", "Banano", 5000));
        minimercado.addProducto(new Producto("SKU-03", "Aguacate", 8000));
    }

    public void inicializarClientes(){
        Minimercado minimercado = Minimercado.getInstancia();
        minimercado.addCliente(new Cliente("109314240", "Julian", "julian2@gmail.com", "3104567890"));
        minimercado.addCliente(new Cliente("1093", "Andrea", "andrea@gmail.com", "3240123943"));
        minimercado.addCliente(new Cliente("1094", "Pablo", "pablo@gmail.com", "3104545604"));
    }

}
