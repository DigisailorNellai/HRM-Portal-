package com.example.HRM_Portal.bean;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EmailData {

    private String recipient;
    private String mailBody;
    private String mailSubject;

    private List<String> attachments;
    private HashMap<String, String> dynamicValues;

    private String applicationName;
    private String templateName;

    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public String getMailBody() {
        return mailBody;
    }
    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }
    public String getMailSubject() {
        return mailSubject;
    }
    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }
    public List<String> getAttachments() {
        return attachments;
    }
    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
    public HashMap<String, String> getDynamicValues() {
        return dynamicValues;
    }
    public void setDynamicValues(HashMap<String, String> dynamicValues) {
        this.dynamicValues = dynamicValues;
    }
    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    public String getTemplateName() {
        return templateName;
    }
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    @Override
    public String toString() {
        return "EmailData [recipient=" + recipient + ", mailBody=" + mailBody + ", mailSubject=" + mailSubject
                + ", attachments=" + attachments + ", dynamicValues=" + dynamicValues + ", applicationName="
                + applicationName + ", templateName=" + templateName + "]";
    }

}