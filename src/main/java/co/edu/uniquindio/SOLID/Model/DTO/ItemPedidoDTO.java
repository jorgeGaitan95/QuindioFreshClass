package co.edu.uniquindio.SOLID.Model.DTO;

public class ItemPedidoDTO {
    public String skuProducto;
    public int cantidad;
    
    public ItemPedidoDTO() {}
    
    public ItemPedidoDTO(String skuProducto, int cantidad) {
        this.skuProducto = skuProducto;
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString() {
        return skuProducto + " x" + cantidad;
    }
}