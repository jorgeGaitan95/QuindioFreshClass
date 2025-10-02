package co.edu.uniquindio.SOLID.Service;

import co.edu.uniquindio.SOLID.Model.ItemPedido;
import co.edu.uniquindio.SOLID.Model.Pedido;
import co.edu.uniquindio.SOLID.Model.Producto;
import co.edu.uniquindio.SOLID.Service.Envio.Envio;

public class PedidoService {
    CatalogoProductosService catalogoProductosService;

    public PedidoService(CatalogoProductosService catalogoProductosService) {
        this.catalogoProductosService = catalogoProductosService;
    }

    public boolean addItemPedido(String idPedido, String sku, int cantidad) {

        try{
            if(opcion ==6)

        }
        Pedido pedido =
        Producto p1 = catalogoProductosService.buscarProducto(sku);
        if (p1 == null)
            return false;

        ItemPedido item = new ItemPedido(p1, cantidad);

    }

    public double calcularSubtotal(Pedido pedido) {
        double subtotal = 0;

        for (ItemPedido item : pedido.getItems()) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }

        return subtotal;
    }

//    public double calcularTotal(Envio envio) {
//        return calcularSubtotal() + envio.calcularCostoEnvio();
//    }
}
