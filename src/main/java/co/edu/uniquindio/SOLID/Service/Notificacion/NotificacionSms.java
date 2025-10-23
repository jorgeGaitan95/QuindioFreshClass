package co.edu.uniquindio.SOLID.Service.Notificacion;

public class NotificacionSms implements Notificacion {

    @Override
    public void enviar(String mensaje) {
        System.out.println("Enviando SMS: " + mensaje);
    }
}
