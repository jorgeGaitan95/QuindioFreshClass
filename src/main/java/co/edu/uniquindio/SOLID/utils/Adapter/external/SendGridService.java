package co.edu.uniquindio.SOLID.utils.Adapter.external;

public class SendGridService {
    private String apiKey;
    
    public SendGridService(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public SendGridResponse send(SendGridEmail email) {
        System.out.println("🔗 Conectando con SendGrid API...");
        System.out.println("   API Key: " + apiKey.substring(0, 8) + "...");
        System.out.println("   From: " + email.getFrom());
        System.out.println("   Subject: " + email.getSubject());
        System.out.println("   To: " + email.getTo());
        
        SendGridResponse response = new SendGridResponse();
        response.setSuccess(true);
        response.setMessageId("msg_" + System.currentTimeMillis());
        
        return response;
    }
}
