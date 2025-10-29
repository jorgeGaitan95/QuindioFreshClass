package co.edu.uniquindio.SOLID.utils.Adapter.external;

public class ServientregaQuoteRequest {
    private String originCity;
    private String originState;
    private String destinationCity;
    private String destinationState;
    private double weight;
    private String serviceType;
    private String clientId;
    
    public String getOriginCity() { return originCity; }
    public void setOriginCity(String originCity) { this.originCity = originCity; }
    public String getOriginState() { return originState; }
    public void setOriginState(String originState) { this.originState = originState; }
    public String getDestinationCity() { return destinationCity; }
    public void setDestinationCity(String destinationCity) { this.destinationCity = destinationCity; }
    public String getDestinationState() { return destinationState; }
    public void setDestinationState(String destinationState) { this.destinationState = destinationState; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
}
