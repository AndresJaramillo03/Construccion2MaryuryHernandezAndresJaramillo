package app.dao.interfaces;

import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import java.util.List;

public interface InvoiceDao {
    
    public void createInvoice(InvoiceDto invoiceDto) throws Exception;
    
    public List<InvoiceDto> findByPartnerId(PartnerDto partnerDto) throws Exception;
}