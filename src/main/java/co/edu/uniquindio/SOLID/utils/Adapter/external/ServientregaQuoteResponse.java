package co.edu.uniquindio.SOLID.utils.Adapter.external;

public class ServientregaQuoteResponse {
    private boolean success;
    private double cost;
    private int estimatedDays;
    private String trackingNumber;
    private String serviceDescription;
    private String errorMessage;
    
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
    public int getEstimatedDays() { return estimatedDays; }
    public void setEstimatedDays(int estimatedDays) { this.estimatedDays = estimatedDays; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public String getServiceDescription() { return serviceDescription; }
    public void setServiceDescription(String serviceDescription) { this.serviceDescription = serviceDescription; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
