package co.edu.uniquindio.SOLID.Model;

public class Empleado {
    private String id;
    private String nombre;
    private Rol rol;
    private boolean activo;

    public enum Rol { CAJERO, BODEGUERO }

    public Empleado(String id, String nombre, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.activo = true;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public Rol getRol() { return rol; }
    public boolean isActivo() { return activo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRol(Rol rol) { this.rol = rol; }
    public void inactivar() { this.activo = false; }
    public void activar() { this.activo = true; }

    public void cambiarRol(Rol nuevoRol) { this.rol = nuevoRol; }
}


