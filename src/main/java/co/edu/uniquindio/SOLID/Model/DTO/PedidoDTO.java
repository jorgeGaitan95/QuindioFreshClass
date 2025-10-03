package co.edu.uniquindio.SOLID.Model.DTO;

import java.util.List;

public class PedidoDTO {
    public String  codigo;
    public String idCliente;
    public List<ItemPedidoDTO> itemsPedido;
    public String direccionEnvio;
    public String notas;
    public String codigoDescuento;
}
