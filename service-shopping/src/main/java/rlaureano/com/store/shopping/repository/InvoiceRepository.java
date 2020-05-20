package rlaureano.com.store.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rlaureano.com.store.shopping.entity.Invoice;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    public List<Invoice> findByCustomerId(Long customerId);
    public Invoice findByNumberInvoice(String numberInvoice);

}
