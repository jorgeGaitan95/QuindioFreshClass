package co.edu.uniquindio.SOLID.Service.Notificacion.Decorator;

import co.edu.uniquindio.SOLID.Service.Notificacion.Notificacion;

public abstract class NotificacionDecorator implements Notificacion {
    protected Notificacion notificacion;

    public NotificacionDecorator(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    @Override
    public void enviar(String mensaje) {
        notificacion.enviar(mensaje);
    }
}

