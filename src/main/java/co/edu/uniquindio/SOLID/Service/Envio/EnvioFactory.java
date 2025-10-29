package co.edu.uniquindio.SOLID.Service.Envio;

import co.edu.uniquindio.SOLID.utils.Adapter.ServientregaAdapter;

public class EnvioFactory {
    public static Envio crearEnvio(String tipoEnvio) {
        switch (tipoEnvio.toLowerCase()) {
            case "estandar":
                return new EnvioEstandar();
            case "express":
                return new EnvioExpress();
            case "servientrega":
                return new ServientregaAdapter("CLIENT_123", "API_KEY_456", "ESTANDAR");
            case "servientrega_express":
                return new ServientregaAdapter("CLIENT_123", "API_KEY_456", "EXPRESS");
            default:
                throw new IllegalArgumentException("Tipo de envío no reconocido: " + tipoEnvio);
        }
    }
}
