package tigers.cave.webm.invoice.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tigers.cave.webm.invoice.dao.model.Invoice;
import tigers.cave.webm.invoice.dao.repository.custom.InvoiceRepositoryCustom;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>, InvoiceRepositoryCustom {

}
