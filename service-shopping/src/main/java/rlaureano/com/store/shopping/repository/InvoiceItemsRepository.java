package rlaureano.com.store.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rlaureano.com.store.shopping.entity.InvoiceItem;

public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem,Long> {

}
