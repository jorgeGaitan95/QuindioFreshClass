package co.edu.uniquindio.SOLID.PatronesCreacionales;

import co.edu.uniquindio.SOLID.Service.MetodoPago;

public class PagoFactory {
    public static MetodoPago crearPago(String tipo) {
        switch (tipo.toLowerCase()) {
            case "tarjeta":
                return new co.edu.uniquindio.SOLID.model.PagoTarjetaCredito();
            case "pse":
                return new co.edu.uniquindio.SOLID.model.PagoPSE();

            default:
                throw new IllegalArgumentException("Tipo de pago no soportado: " + tipo);
        }
    }
}
