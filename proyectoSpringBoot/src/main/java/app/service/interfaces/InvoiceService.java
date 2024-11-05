package app.service.interfaces;

import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import java.util.List;

public interface InvoiceService {
    
    public void createInvoice(List<InvoiceDetailDto> invoiceDtoList) throws Exception;
    
    public void updateInvoice(InvoiceDto invoiceDto) throws Exception;
}
