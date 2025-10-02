package co.edu.uniquindio.SOLID;

import co.edu.uniquindio.SOLID.Service.Notificacion.NotificacionFactory;
import co.edu.uniquindio.SOLID.Service.Pago.PagoFactory;
import co.edu.uniquindio.SOLID.Model.PedidoBuilder;
import co.edu.uniquindio.SOLID.Model.*;
import co.edu.uniquindio.SOLID.Service.Envio.Envio;
import co.edu.uniquindio.SOLID.Service.Envio.EnvioExpress;
import co.edu.uniquindio.SOLID.Service.Notificacion.Notificacion;
import co.edu.uniquindio.SOLID.Service.Pago.MetodoPago;
import co.edu.uniquindio.SOLID.utils.AppSetup;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AppSetup app = new AppSetup();

        Cliente cliente = new Cliente("109314240", "Julian", "julian2@gmail.com", "3104567890");


        List<ItemPedido> items = new ArrayList<>();

        Producto p1 = catalogo.buscarProducto("SKU-01");
        if (p1 != null) items.add(new ItemPedido(p1, 1));

        Producto p2 = catalogo.buscarProducto("SKU-02");
        if (p2 != null) items.add(new ItemPedido(p2, 2));

        Producto p3 = catalogo.buscarProducto("SKU-03");
        if (p3 != null) items.add(new ItemPedido(p3, 3));


        Pedido pedido = new PedidoBuilder()
                .withCodigo("PED-001")
                .withCliente(cliente)
                .withItems(items)
                .direccionEnvio("Cra 12 #34-56")
                .notas("Entregar en horario laboral")
                .build();

        System.out.println("Pedido creado con éxito:");
        System.out.println(pedido);


        Envio envio = new EnvioExpress();
        double total = pedido.calcularTotal(envio);
        System.out.println("Total del pedido $ " + total);


        MetodoPago pago = PagoFactory.crearPago("TARJETA");
        pago.procesarPago(total);


        Notificacion notificacion = NotificacionFactory.crearNotificacion("EMAIL");
        notificacion.enviar("Su pedido " + pedido.getCodigo() + " ha sido procesado exitosamente.");
        }
    }


         if (producto == null)
        throw new IllegalArgumentException("El producto no puede ser nulo");
        if (cantidad <= 0)
        throw new IllegalArgumentException("La cantidad debe ser mayor que cero");