package app.dto;

public class Detail_InvoiceDto {
    private long id; 
    private InvoiceDto invoiceId; 
    private int item;
    private String description;
    private double amount;

    public Detail_InvoiceDto() {
    }
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public InvoiceDto getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(InvoiceDto invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    
    
}
