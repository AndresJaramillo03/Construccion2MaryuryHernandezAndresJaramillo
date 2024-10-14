package app.dao.interfaces;

import app.dto.InvoiceDetailDto;

public interface InvoiceDetailDao {
    public void createInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws Exception;
    
    public InvoiceDetailDto findById (InvoiceDetailDto invoiceDetailDto) throws Exception;
}