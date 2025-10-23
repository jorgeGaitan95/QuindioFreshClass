package co.edu.uniquindio.SOLID.model.DTO;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClienteDTO {
    private StringProperty cedula;
    private StringProperty nombre;
    private StringProperty correo;
    private StringProperty telefono;

    public ClienteDTO() {
        this.cedula = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
        this.correo = new SimpleStringProperty();
        this.telefono = new SimpleStringProperty();
    }

    public ClienteDTO(String cedula, String nombre, String correo, String telefono) {
        this();
        setCedula(cedula);
        setNombre(nombre);
        setCorreo(correo);
        setTelefono(telefono);
    }

    public String getCedula() { return cedula.get(); }
    public String getNombre() { return nombre.get(); }
    public String getCorreo() { return correo.get(); }
    public String getTelefono() { return telefono.get(); }

    public void setCedula(String cedula) { this.cedula.set(cedula); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public void setCorreo(String correo) { this.correo.set(correo); }
    public void setTelefono(String telefono) { this.telefono.set(telefono); }

    public StringProperty cedulaProperty() { return cedula; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty correoProperty() { return correo; }
    public StringProperty telefonoProperty() { return telefono; }

    @Override
    public String toString() {
        return getNombre() + " (" + getCedula() + ")";
    }
}
