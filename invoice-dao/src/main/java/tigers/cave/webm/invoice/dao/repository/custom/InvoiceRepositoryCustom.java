package tigers.cave.webm.invoice.dao.repository.custom;

import java.util.List;

import tigers.cave.webm.invoice.dao.model.Invoice;
import tigers.cave.webm.invoice.dao.repository.criteria.InvoiceCriteria;

public interface InvoiceRepositoryCustom {

	List<Invoice> findByCriteria(InvoiceCriteria invoiceCriteria);

}
