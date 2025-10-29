package co.edu.uniquindio.SOLID.Service.Pago;

import co.edu.uniquindio.SOLID.utils.Adapter.PayUAdapter;

public class PagoFactory {
    public static MetodoPago crearPago(String tipo) {
        switch (tipo.toLowerCase()) {
            case "tarjeta":
                return new PagoTarjetaCredito();
            case "pse":
                return new PagoPSE();
            case "payu":
                return new PayUAdapter("MERCHANT_123", "API_KEY_456");
            default:
                throw new IllegalArgumentException("Tipo de pago no soportado: " + tipo);
        }
    }
}
