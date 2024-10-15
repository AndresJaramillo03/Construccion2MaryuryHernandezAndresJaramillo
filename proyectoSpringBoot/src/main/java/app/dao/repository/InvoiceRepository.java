package app.dao.repository;

import app.model.Invoice;
import app.model.Partner;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long>{
    public List<Invoice> findByPartnerId(Partner partner);
}