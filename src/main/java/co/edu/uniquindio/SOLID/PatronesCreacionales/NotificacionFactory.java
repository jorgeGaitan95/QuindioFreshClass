package co.edu.uniquindio.SOLID.PatronesCreacionales;

import co.edu.uniquindio.SOLID.Service.Notificacion;
import co.edu.uniquindio.SOLID.model.NotificacionEmail;
import co.edu.uniquindio.SOLID.model.NotificacionSms;

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