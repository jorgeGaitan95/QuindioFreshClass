package co.edu.uniquindio.SOLID.Service.Envio;

public class EnvioEstandar implements Envio {

    @Override
    public double calcularCostoEnvio() {
        double costo = 7000;
        System.out.println("Costo de envío estándar: " + costo);
        return costo;
    }

}