package co.edu.uniquindio.SOLID.Model;

public class Producto {
    private String Sku;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String sku, String nombre, double precio) {
        this.Sku = sku;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = 0;
    }

    public String getSku() {return Sku;}
    public String getNombre() {return nombre;}
    public double getPrecio() {return precio;}
    public int getStock() {return stock;}
    
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setPrecio(double precio) {this.precio = precio;}

    public void aumentarStock(int cantidad) {
        if (cantidad > 0) this.stock += cantidad;
    }

    public void disminuirStock(int cantidad) {
        if (cantidad > 0 && this.stock - cantidad >= 0) this.stock -= cantidad;
    }

    public boolean tieneStockSuficiente(int cantidadRequerida) {
        return cantidadRequerida >= 0 && this.stock >= cantidadRequerida;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : (Sku != null ? Sku : "");
    }
}
