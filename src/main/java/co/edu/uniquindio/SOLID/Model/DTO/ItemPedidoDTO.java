package co.edu.uniquindio.SOLID.Model.DTO;

import java.security.PublicKey;

public class ItemPedidoDTO {
    public String codigo;
    public int cantidad;

    public ItemPedidoDTO(String codigo, int cantidad) {
        this.codigo = codigo;
        this.cantidad = cantidad;
    }
}
