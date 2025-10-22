package co.edu.uniquindio.SOLID.Service.Fachadas;

import co.edu.uniquindio.SOLID.Model.DTO.PedidoDTO;
import co.edu.uniquindio.SOLID.Service.CatalogoProductosService;
import co.edu.uniquindio.SOLID.Service.PedidoService;

import java.security.PublicKey;

public class PedidoFacade {
    private PedidoService pedidoService;
    private CatalogoProductosService catalogoProductosService;

    public PedidoFacade (){
        catalogoProductosService = new CatalogoProductosService();
        pedidoService = new PedidoService(catalogoProductosService);
    }

    public PedidoDTO crearPedido (PedidoDTO pedidoDTO) {
        pedidoService.crearPedido(pedidoDTO);
        return pedidoDTO;
    }
}
