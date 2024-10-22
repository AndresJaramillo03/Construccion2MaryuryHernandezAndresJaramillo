package app.dao;

import app.dao.interfaces.InvoiceDao;
import app.dao.repository.InvoiceRepository;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import app.helpers.Helper;
import app.model.Invoice;
import app.model.Partner;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<InvoiceDto> findByPartnerId(PartnerDto partnerDto) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        List<Invoice> invoices = invoiceRepository.findByPartnerId(partner);
        List<InvoiceDto> invoicesDto = new ArrayList<InvoiceDto>();
        for (Invoice invoice : invoices){
        invoicesDto.add(Helper.parse(invoice));
        }
        return invoicesDto;
    } 
    
    @Override
    public void updateInvoice(InvoiceDto invoiceDto) throws Exception {
        String sql = "UPDATE invoices SET total_amount = ?, status = ? WHERE id = ?";
    }

}