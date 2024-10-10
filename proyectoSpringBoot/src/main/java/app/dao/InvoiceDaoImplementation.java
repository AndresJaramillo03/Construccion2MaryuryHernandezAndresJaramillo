package app.dao;

import app.dao.interfaces.InvoiceDao;
import app.dao.repository.InvoiceRepository;
import app.dto.InvoiceDto;
import app.helpers.Helper;
import app.model.Invoice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Getter
@Setter
@Service
@NoArgsConstructor

public class InvoiceDaoImplementation implements InvoiceDao {
    @Autowired
    InvoiceRepository invoiceRepository;
    
    @Override
    public void createInvoice(InvoiceDto invoiceDto) throws Exception {
        Invoice invoice = Helper.parse(invoiceDto);
        invoiceRepository.save(invoice);
        invoiceDto.setId(invoice.getId());
    } 
    
    
}