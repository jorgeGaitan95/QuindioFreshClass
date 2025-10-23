package co.edu.uniquindio.SOLID.Service;

import co.edu.uniquindio.SOLID.model.*;
import co.edu.uniquindio.SOLID.model.DTO.ItemPedidoDTO;
import co.edu.uniquindio.SOLID.model.DTO.PedidoDTO;
import co.edu.uniquindio.SOLID.Service.Envio.Envio;
import co.edu.uniquindio.SOLID.Service.Envio.EnvioExpress;
import co.edu.uniquindio.SOLID.Service.Notificacion.Notificacion;
import co.edu.uniquindio.SOLID.Service.Notificacion.NotificacionFactory;
import co.edu.uniquindio.SOLID.Service.Pago.MetodoPago;
import co.edu.uniquindio.SOLID.Service.Pago.PagoFactory;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    CatalogoProductosService catalogoProductosService;
    ClienteService clienteService;

    public PedidoService(CatalogoProductosService catalogoProductosService) {
        this.catalogoProductosService = catalogoProductosService;
        this.clienteService = new ClienteService();
    }

    public Pedido crearPedido(PedidoDTO pedidoDTO) {

        List<ItemPedido> items = new ArrayList<ItemPedido>();

        for (ItemPedidoDTO item : pedidoDTO.itemsPedido) {
            Producto producto = catalogoProductosService.buscarProducto(item.skuProducto);
            if (producto != null) {
                ItemPedido itemPedido = new ItemPedido(producto, item.cantidad);
                items.add(itemPedido);
            }
        }

        Cliente cliente = clienteService.buscarCliente(pedidoDTO.idCliente);

        PedidoBuilder pedidoBuilder = new PedidoBuilder(pedidoDTO.codigo,cliente,items, pedidoDTO.direccionEnvio);

        if (pedidoDTO.notas != null || !pedidoDTO.notas.isEmpty()){
            pedidoBuilder.withNotas(pedidoDTO.notas);
        }

        if (pedidoDTO.codigoDescuento !=null || !pedidoDTO.codigoDescuento.isEmpty()){
            pedidoBuilder.withCodigoDescuento(pedidoDTO.codigoDescuento);
        }

        Pedido pedido = pedidoBuilder.build();

        Envio envio = new EnvioExpress();
        double total = calcularTotal(pedido, envio);
        System.out.println("Total del pedido $ " + total);

        MetodoPago pago = PagoFactory.crearPago("TARJETA");
        pago.procesarPago(total);


        Notificacion notificacion = NotificacionFactory.crearNotificacion("EMAIL");
        notificacion.enviar("Su pedido " + pedido.getCodigo() + " ha sido procesado exitosamente.");

        return pedido;
    }

    public double calcularSubtotal(Pedido pedido) {
        double subtotal = 0;

        for (ItemPedido item : pedido.getItems()) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }

        return subtotal;
    }

  public double calcularTotal(Pedido pedido, Envio envio) {
        return calcularSubtotal(pedido) + envio.calcularCostoEnvio();
    }
}
