package co.edu.uniquindio.SOLID.Service.Envio;

public class EnvioExpress implements Envio {

    @Override
    public double calcularCostoEnvio() {
        double costo = 15000;
        System.out.println("Costo de envío express: " + costo);
        return costo;
    }

}
