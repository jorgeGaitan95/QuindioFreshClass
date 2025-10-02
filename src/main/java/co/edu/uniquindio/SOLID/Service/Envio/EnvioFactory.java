package co.edu.uniquindio.SOLID.Service.Envio;

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
