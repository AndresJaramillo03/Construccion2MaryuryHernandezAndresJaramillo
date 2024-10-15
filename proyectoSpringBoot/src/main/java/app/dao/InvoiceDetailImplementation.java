package app.dao;

import app.dao.interfaces.InvoiceDao;
import app.dao.interfaces.InvoiceDetailDao;
import app.dao.repository.InvoiceDetailRepository;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.helpers.Helper;
import app.model.InvoiceDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service

public class InvoiceDetailImplementation implements  InvoiceDetailDao {
    @Autowired
    InvoiceDetailRepository invoiceDetailRepository;
    
    @Override
    public void createInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws Exception {
            InvoiceDetail invoiceDetail = Helper.parse(invoiceDetailDto);
            invoiceDetailRepository.save(invoiceDetail);
            invoiceDetailDto.setId(invoiceDetail.getId());
    }
    
    @Override
    public InvoiceDetailDto findById (InvoiceDetailDto invoiceDetailDto) throws Exception {
        InvoiceDetail invoiceDetal = invoiceDetailRepository.findById(invoiceDetailDto.getId());
        return Helper.parse(invoiceDetal);
    }  
}