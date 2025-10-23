package co.edu.uniquindio.SOLID.model.DTO;

public class ClienteDTO {
    public String cedula;
    public String nombre;
    public String correo;
    public String telefono;
    
    public ClienteDTO() {}
    
    public ClienteDTO(String cedula, String nombre, String correo, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }
    
    @Override
    public String toString() {
        return nombre + " (" + cedula + ")";
    }
}