package co.edu.uniquindio.SOLID;

import co.edu.uniquindio.SOLID.PatronesCreacionales.EnvioFactory;
import co.edu.uniquindio.SOLID.PatronesCreacionales.NotificacionFactory;
import co.edu.uniquindio.SOLID.PatronesCreacionales.PagoFactory;
import co.edu.uniquindio.SOLID.PatronesCreacionales.PedidoBuilder;
import co.edu.uniquindio.SOLID.Service.Envio;
import co.edu.uniquindio.SOLID.Service.MetodoPago;
import co.edu.uniquindio.SOLID.Service.Notificacion;
import co.edu.uniquindio.SOLID.model.*;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CatalogoProductos catalogo = CatalogoProductos.getInstancia();
        catalogo.agregarProducto(new Producto("SKU-01", "Café Quindío", 25000));
        catalogo.agregarProducto(new Producto("SKU-02", "Banano", 5000));
        catalogo.agregarProducto(new Producto("SKU-03", "Aguacate", 8000));

        catalogo.mostrarCatalogo();


        Cliente cliente = new Cliente("109314240", "Julian", "julian2@gmail.com", "3104567890");


        List<ItemPedido> items = new ArrayList<>();

        Producto p1 = catalogo.buscarProducto("SKU-01");
        if (p1 != null) items.add(new ItemPedido(p1, 1));

        Producto p2 = catalogo.buscarProducto("SKU-02");
        if (p2 != null) items.add(new ItemPedido(p2, 2));

        Producto p3 = catalogo.buscarProducto("SKU-03");
        if (p3 != null) items.add(new ItemPedido(p3, 3));


        Pedidos pedido = new PedidoBuilder()
                .codigo("PED-001")
                .Cliente(cliente)
                .items(items)
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