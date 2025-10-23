package co.edu.uniquindio.SOLID.model.DTO;

public class ProductoDTO {
    public String sku;
    public String nombre;
    public double precio;
    
    public ProductoDTO() {}
    
    public ProductoDTO(String sku, String nombre, double precio) {
        this.sku = sku;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }
}