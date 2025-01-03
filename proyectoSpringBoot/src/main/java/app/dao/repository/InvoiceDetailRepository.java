package app.dao.repository;

import app.model.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceDetailRepository extends JpaRepository <InvoiceDetail,Long> {
    
    public InvoiceDetail findById (long id);
}