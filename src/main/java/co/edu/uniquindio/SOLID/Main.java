package co.edu.uniquindio.SOLID;

import co.edu.uniquindio.SOLID.Model.DTO.ItemPedidoDTO;
import co.edu.uniquindio.SOLID.Model.DTO.PedidoDTO;
import co.edu.uniquindio.SOLID.Service.CatalogoProductosService;
import co.edu.uniquindio.SOLID.Service.Fachadas.PedidoFacade;
import co.edu.uniquindio.SOLID.Service.Notificacion.NotificacionFactory;
import co.edu.uniquindio.SOLID.Service.Pago.PagoFactory;
import co.edu.uniquindio.SOLID.Model.PedidoBuilder;
import co.edu.uniquindio.SOLID.Model.*;
import co.edu.uniquindio.SOLID.Service.Envio.Envio;
import co.edu.uniquindio.SOLID.Service.Envio.EnvioExpress;
import co.edu.uniquindio.SOLID.Service.Notificacion.Notificacion;
import co.edu.uniquindio.SOLID.Service.Pago.MetodoPago;
import co.edu.uniquindio.SOLID.Service.PedidoService;
import co.edu.uniquindio.SOLID.utils.AppSetup;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        AppSetup app = new AppSetup();
        PedidoFacade pedidoFacade = new PedidoFacade();

            List<ItemPedidoDTO> ProductosPedido = new ArrayList<>();
            ProductosPedido.add(new ItemPedidoDTO("SKU-01",1));
            ProductosPedido.add(new ItemPedidoDTO("SKU-02",2));
            ProductosPedido.add(new ItemPedidoDTO("SKU-03",4));

            PedidoDTO pedidoDTO = new PedidoDTO();
            pedidoDTO.codigo = "OO1";
            pedidoDTO.idCliente = "1093";
            pedidoDTO.itemsPedido = ProductosPedido;
            pedidoDTO.direccionEnvio = "Cra 5c - 27";

          pedidoFacade.crearPedido(pedidoDTO);
        }
    }