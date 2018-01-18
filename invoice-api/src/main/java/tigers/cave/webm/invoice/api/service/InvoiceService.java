package tigers.cave.webm.invoice.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tigers.cave.webm.invoice.dao.model.Invoice;
import tigers.cave.webm.invoice.dao.repository.InvoiceRepository;

@Service
public class InvoiceService {

	@Autowired
	InvoiceRepository invoiceRepository;

	public void findInvoice(String invoiceNo) {

	}

	public List<Invoice> findAllInvoicesByCriteria() {

		List<Invoice> list = invoiceRepository.findAll();
		return list;
	}

	public void createInvoice() {

	}

}
