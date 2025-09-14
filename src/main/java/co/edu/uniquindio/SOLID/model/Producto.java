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
    public void setSku(String sku) {this.Sku = sku;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public double getPrecio() {return precio;}
    public void setPrecio(double precio) {this.precio = precio;}
    @Override
    public String toString() {
        return "Producto{" +
                "Sku='" + Sku + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return Sku.equals(producto.Sku);

    }
    @Override
    public int hashCode() {
        return Sku.hashCode();
    }
}



