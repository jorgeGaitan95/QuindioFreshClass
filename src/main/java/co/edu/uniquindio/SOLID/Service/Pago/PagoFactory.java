package co.edu.uniquindio.SOLID.Service.Pago;

public class PagoFactory {
    public static MetodoPago crearPago(String tipo) {
        switch (tipo.toLowerCase()) {
            case "tarjeta":
                return new PagoTarjetaCredito();
            case "pse":
                return new PagoPSE();

            default:
                throw new IllegalArgumentException("Tipo de pago no soportado: " + tipo);
        }
    }
}
