package co.edu.uniquindio.SOLID.Model.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class ResumenPedidoDTO {
    public String codigo;
    public LocalDateTime fechaCreacion;
    public String nombreCliente;
    public String correoCliente;
    public List<ItemPedidoDTO> items;
    public String direccionEnvio;
    public String notas;
    public String codigoDescuento;
    public double subtotal;
    public double costoEnvio;
    public double total;
    public String metodoPago;
    public String tipoEnvio;
    public String tipoNotificacion;
    public String estado;

    public ResumenPedidoDTO() {}

    @Override
    public String toString() {
        return String.format("Pedido %s - %s - Total: $%.2f", 
            codigo, nombreCliente, total);
    }
}

