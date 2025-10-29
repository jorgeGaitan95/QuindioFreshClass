package co.edu.uniquindio.SOLID.utils.Adapter.external;

public class PayUAPI {
    private String merchantId;
    private String apiKey;
    
    public PayUAPI(String merchantId, String apiKey) {
        this.merchantId = merchantId;
        this.apiKey = apiKey;
    }
    
    public PayUPaymentResponse processPayment(PayUPaymentRequest request) {
        System.out.println("Conectando con PayU API...");
        System.out.println("   Merchant ID: " + merchantId);
        System.out.println("   Procesando pago de: $" + request.getAmount());
        System.out.println("   Referencia: " + request.getReferenceCode());
        
        PayUPaymentResponse response = new PayUPaymentResponse();
        response.setStatus("APPROVED");
        response.setTransactionId("TXN-" + System.currentTimeMillis());
        response.setMessage("Pago procesado exitosamente");
        
        return response;
    }
}
