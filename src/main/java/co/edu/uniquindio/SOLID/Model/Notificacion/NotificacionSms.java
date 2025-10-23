package co.edu.uniquindio.SOLID.Model.Notificacion;

public class NotificacionSms implements Notificacion {

    @Override
    public void enviar(String mensaje) {
        System.out.println("Enviando SMS: " + mensaje);
    }
}
