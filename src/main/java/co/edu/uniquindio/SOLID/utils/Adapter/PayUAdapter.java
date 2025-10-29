package co.edu.uniquindio.SOLID.utils.Adapter;

import co.edu.uniquindio.SOLID.Service.Pago.MetodoPago;
import co.edu.uniquindio.SOLID.utils.Adapter.external.PayUAPI;
import co.edu.uniquindio.SOLID.utils.Adapter.external.PayUPaymentRequest;
import co.edu.uniquindio.SOLID.utils.Adapter.external.PayUPaymentResponse;

public class PayUAdapter implements MetodoPago {
    
    private PayUAPI payUAPI;
    private String merchantId;
    private String apiKey;
    
    public PayUAdapter(String merchantId, String apiKey) {
        this.merchantId = merchantId;
        this.apiKey = apiKey;
        this.payUAPI = new PayUAPI(merchantId, apiKey);
    }
    
    @Override
    public boolean procesarPago(double monto) {
        try {
            PayUPaymentRequest request = new PayUPaymentRequest();
            request.setAmount(monto);
            request.setCurrency("COP");
            request.setMerchantId(merchantId);
            request.setDescription("Pago pedido Quindío Fresh");
            request.setReferenceCode("PED-" + System.currentTimeMillis());
            
            PayUPaymentResponse response = payUAPI.processPayment(request);
            
            return response.getStatus().equals("APPROVED");
            
        } catch (Exception e) {
            System.err.println("Error procesando pago con PayU: " + e.getMessage());
            return false;
        }
    }
}
