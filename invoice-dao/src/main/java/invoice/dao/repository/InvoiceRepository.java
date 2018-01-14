package invoice.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import invoice.dao.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

}
