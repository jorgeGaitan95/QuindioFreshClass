package co.edu.uniquindio.SOLID.utils.Adapter.external;

public class SendGridResponse {
    private boolean success;
    private String messageId;
    private String errorMessage;
    
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
