package co.edu.uniquindio.SOLID.Service.Notificacion;

public class NotificacionFactory {
    public static Notificacion crearNotificacion(String tipo) {
        if (tipo.equalsIgnoreCase("email")) {
            return new NotificacionEmail();
        } else if (tipo.equalsIgnoreCase("sms")) {
            return new NotificacionSms();
        }
        throw new IllegalArgumentException("Tipo de notificación no soportado");
    }
}
