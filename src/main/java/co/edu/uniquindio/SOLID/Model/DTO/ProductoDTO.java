package co.edu.uniquindio.SOLID.Model.DTO;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductoDTO {
    private StringProperty sku;
    private StringProperty nombre;
    private DoubleProperty precio;

    public ProductoDTO() {
        this.sku = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
        this.precio = new SimpleDoubleProperty();
    }

    public ProductoDTO(String sku, String nombre, double precio) {
        this();
        setSku(sku);
        setNombre(nombre);
        setPrecio(precio);
    }

    public String getSku() { return sku.get(); }
    public String getNombre() { return nombre.get(); }
    public double getPrecio() { return precio.get(); }

    public void setSku(String sku) { this.sku.set(sku); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public void setPrecio(double precio) { this.precio.set(precio); }

    public StringProperty skuProperty() { return sku; }
    public StringProperty nombreProperty() { return nombre; }
    public DoubleProperty precioProperty() { return precio; }

    @Override
    public String toString() {
        return getNombre() + " - $" + String.format("%.0f", getPrecio());
    }
}
