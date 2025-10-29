package co.edu.uniquindio.SOLID.utils.Adapter;

import co.edu.uniquindio.SOLID.Service.Envio.Envio;
import co.edu.uniquindio.SOLID.utils.Adapter.external.ServientregaAPI;
import co.edu.uniquindio.SOLID.utils.Adapter.external.ServientregaQuoteRequest;
import co.edu.uniquindio.SOLID.utils.Adapter.external.ServientregaQuoteResponse;

public class ServientregaAdapter implements Envio {
    
    private ServientregaAPI servientregaAPI;
    private String clientId;
    private String apiKey;
    private String tipoEnvio;
    
    public ServientregaAdapter(String clientId, String apiKey, String tipoEnvio) {
        this.clientId = clientId;
        this.apiKey = apiKey;
        this.tipoEnvio = tipoEnvio;
        this.servientregaAPI = new ServientregaAPI(clientId, apiKey);
    }
    
    @Override
    public double calcularCostoEnvio() {
        try {
            ServientregaQuoteRequest request = new ServientregaQuoteRequest();
            request.setOriginCity("Armenia");
            request.setOriginState("Quindío");
            request.setDestinationCity("Bogotá");
            request.setDestinationState("Cundinamarca");
            request.setWeight(2.0);
            request.setServiceType(tipoEnvio);
            request.setClientId(clientId);
            
            ServientregaQuoteResponse response = servientregaAPI.getQuote(request);
            
            if (response.isSuccess()) {
                System.out.println("📦 Cotización Servientrega:");
                System.out.println("   Tipo: " + tipoEnvio);
                System.out.println("   Costo: $" + response.getCost());
                System.out.println("   Días estimados: " + response.getEstimatedDays());
                return response.getCost();
            } else {
                System.err.println("❌ Error en cotización: " + response.getErrorMessage());
                return tipoEnvio.equals("EXPRESS") ? 15000.0 : 7000.0;
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error calculando envío con Servientrega: " + e.getMessage());
            return tipoEnvio.equals("EXPRESS") ? 15000.0 : 7000.0;
        }
    }
}
