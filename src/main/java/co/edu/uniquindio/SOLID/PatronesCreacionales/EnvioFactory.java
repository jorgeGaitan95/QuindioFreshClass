package co.edu.uniquindio.SOLID.PatronesCreacionales;

import co.edu.uniquindio.SOLID.Service.Envio;
import co.edu.uniquindio.SOLID.model.EnvioEstandar;
import co.edu.uniquindio.SOLID.model.EnvioExpress;

public class EnvioFactory {
    public static Envio crearEnvio(String tipoEnvio) {
        switch (tipoEnvio.toLowerCase()) {
            case "estandar":
                return new EnvioEstandar();
            case "express":
                return new EnvioExpress();
            default:
                throw new IllegalArgumentException("Tipo de envío no reconocido: " + tipoEnvio);
        }
    }
}
