package co.edu.uniquindio.SOLID.model;

public class Producto {
    private String Sku;
    private String nombre;
    private double precio;

    public Producto(String sku, String nombre, double precio) {
        this.Sku = sku;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getSku() {return Sku;}
    public String getNombre() {return nombre;}
    public double getPrecio() {return precio;}
    
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setPrecio(double precio) {this.precio = precio;}
}
