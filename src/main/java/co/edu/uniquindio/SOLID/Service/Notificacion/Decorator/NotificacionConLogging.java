package co.edu.uniquindio.SOLID.Service.Notificacion.Decorator;

import co.edu.uniquindio.SOLID.Service.Notificacion.Notificacion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificacionConLogging extends NotificacionDecorator {

    public NotificacionConLogging(Notificacion notificacion) {
        super(notificacion);
    }

    @Override
    public void enviar(String mensaje) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        System.out.println("[" + timestamp + "] 📝 Iniciando envío de notificación...");
        System.out.println("[" + timestamp + "] Mensaje: " + mensaje);
        
        this.notificacion.enviar(mensaje);
        
        System.out.println("[" + timestamp + "] ✅ Notificación enviada exitosamente");
    }
}

