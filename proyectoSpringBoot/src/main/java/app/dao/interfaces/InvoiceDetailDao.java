package app.dao.interfaces;

import app.dto.InvoiceDetailDto;
import java.util.List;

public interface InvoiceDetailDao {
    
    public void createInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws Exception;
    
    public InvoiceDetailDto findById (InvoiceDetailDto invoiceDetailDto) throws Exception;
        public List<InvoiceDetailDto> findAllInvocesDetail() throws Exception;
}