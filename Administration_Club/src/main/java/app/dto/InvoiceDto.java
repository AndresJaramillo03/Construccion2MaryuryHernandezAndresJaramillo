package app.dto;

import java.util.Date;

public class InvoiceDto {
    private long id; 
    private UserDto userId; 
    private PartnerDto partnerId;
    private Date creationDate;
    private double totalAmount;
    private boolean status; 
    
}
