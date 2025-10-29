package co.edu.uniquindio.SOLID.utils.Adapter.external;

public class ServientregaAPI {
    private String clientId;
    private String apiKey;
    
    public ServientregaAPI(String clientId, String apiKey) {
        this.clientId = clientId;
        this.apiKey = apiKey;
    }
    
    public ServientregaQuoteResponse getQuote(ServientregaQuoteRequest request) {
        System.out.println("🔗 Conectando con Servientrega API...");
        System.out.println("   Client ID: " + clientId);
        System.out.println("   Origen: " + request.getOriginCity() + ", " + request.getOriginState());
        System.out.println("   Destino: " + request.getDestinationCity() + ", " + request.getDestinationState());
        System.out.println("   Peso: " + request.getWeight() + " kg");
        System.out.println("   Servicio: " + request.getServiceType());
        
        ServientregaQuoteResponse response = new ServientregaQuoteResponse();
        response.setSuccess(true);
        
        double baseCost = request.getServiceType().equals("EXPRESS") ? 12000.0 : 5000.0;
        double weightMultiplier = request.getWeight() * 1000;
        double distanceMultiplier = 2000;
        double totalCost = baseCost + weightMultiplier + distanceMultiplier;
        
        response.setCost(totalCost);
        response.setEstimatedDays(request.getServiceType().equals("EXPRESS") ? 1 : 3);
        response.setTrackingNumber("ST" + System.currentTimeMillis());
        response.setServiceDescription("Servientrega " + request.getServiceType());
        
        return response;
    }
}
