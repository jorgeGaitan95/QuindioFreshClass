package co.edu.uniquindio.SOLID.utils.Adapter;

import co.edu.uniquindio.SOLID.Service.Notificacion.Notificacion;
import co.edu.uniquindio.SOLID.utils.Adapter.external.SendGridService;
import co.edu.uniquindio.SOLID.utils.Adapter.external.SendGridEmail;
import co.edu.uniquindio.SOLID.utils.Adapter.external.SendGridResponse;

public class SendGridAdapter implements Notificacion {
    
    private SendGridService sendGridService;
    private String fromEmail;
    private String fromName;
    
    public SendGridAdapter(String apiKey, String fromEmail, String fromName) {
        this.fromEmail = fromEmail;
        this.fromName = fromName;
        this.sendGridService = new SendGridService(apiKey);
    }
    
    @Override
    public void enviar(String mensaje) {
        try {
            SendGridEmail email = new SendGridEmail();
            email.setFrom(fromEmail, fromName);
            email.setSubject("Quindío Fresh - Actualización de Pedido");
            email.setHtmlContent(crearHtmlContent(mensaje));
            email.setPlainTextContent(mensaje);
            email.addTo("cliente@quindiofresh.com");
            
            SendGridResponse response = sendGridService.send(email);
            
            if (response.isSuccess()) {
                System.out.println("✅ Email enviado exitosamente vía SendGrid");
                System.out.println("   Message ID: " + response.getMessageId());
            } else {
                System.err.println("❌ Error enviando email: " + response.getErrorMessage());
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error en SendGridAdapter: " + e.getMessage());
        }
    }
    
    private String crearHtmlContent(String mensaje) {
        return String.format("""
            <html>
                <body style="font-family: Arial, sans-serif;">
                    <div style="background-color: #2E8B57; color: white; padding: 20px; text-align: center;">
                        <h1>🌱 Quindío Fresh</h1>
                    </div>
                    <div style="padding: 20px;">
                        <h2>Actualización de Pedido</h2>
                        <p>%s</p>
                        <hr>
                        <p style="color: #666; font-size: 12px;">
                            Gracias por elegir nuestros productos del Eje Cafetero
                        </p>
                    </div>
                </body>
            </html>
            """, mensaje);
    }
}
