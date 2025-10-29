package co.edu.uniquindio.SOLID.utils.Adapter.external;

public class SendGridEmail {
    private String from;
    private String fromName;
    private String subject;
    private String htmlContent;
    private String plainTextContent;
    private String to;
    
    public String getFrom() { return from; }
    public void setFrom(String from, String fromName) { 
        this.from = from; 
        this.fromName = fromName;
    }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getHtmlContent() { return htmlContent; }
    public void setHtmlContent(String htmlContent) { this.htmlContent = htmlContent; }
    public String getPlainTextContent() { return plainTextContent; }
    public void setPlainTextContent(String plainTextContent) { this.plainTextContent = plainTextContent; }
    public void addTo(String email) { this.to = email; }
    public String getTo() { return to; }
}
