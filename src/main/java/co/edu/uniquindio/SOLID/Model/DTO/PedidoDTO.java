package co.edu.uniquindio.SOLID.Model.DTO;

import java.util.List;

public class PedidoDTO {
    public String codigo;
    public String idCliente;
    public List<ItemPedidoDTO> itemsPedido;
    public String direccionEnvio;
    public String notas;
    public String codigoDescuento;
    
    public PedidoDTO() {}
    
    public PedidoDTO(String codigo, String idCliente, List<ItemPedidoDTO> itemsPedido, String direccionEnvio) {
        this.codigo = codigo;
        this.idCliente = idCliente;
        this.itemsPedido = itemsPedido;
        this.direccionEnvio = direccionEnvio;
    }
    
    @Override
    public String toString() {
        return "Pedido{" +
                "codigo='" + codigo + '\'' +
                ", idCliente='" + idCliente + '\'' +
                ", items=" + (itemsPedido != null ? itemsPedido.size() : 0) +
                ", direccionEnvio='" + direccionEnvio + '\'' +
                '}';
    }
}