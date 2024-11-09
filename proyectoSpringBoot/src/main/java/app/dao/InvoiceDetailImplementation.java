package app.dao;

import app.dao.interfaces.InvoiceDetailDao;
import app.dao.repository.InvoiceDetailRepository;
import app.dto.InvoiceDetailDto;
import app.helpers.Helper;
import app.model.InvoiceDetail;
import java.util.List;
import java.util.stream.Collectors;
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
    
    @Override
    public List<InvoiceDetailDto> findAllInvocesDetail() throws Exception {
        List<InvoiceDetail> invoicesDetail = invoiceDetailRepository.findAll();

        if(invoicesDetail.isEmpty()) throw new Exception("No se encontraron detalles de facturas");

        return invoicesDetail.stream().map(Helper::parse).collect(Collectors.toList());
    }
}