
package app.service.interfaces;

import app.dto.InvoiceDetailDto;
import java.util.List;

public interface InvoiceService {
    
    public void createInvoice(List<InvoiceDetailDto> invoiceDtoList) throws Exception;
}
