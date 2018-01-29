package tigers.cave.webm.invoice.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import tigers.cave.webm.invoice.api.resource.InvoiceDetailResource;
import tigers.cave.webm.invoice.api.resource.InvoiceListResource;
import tigers.cave.webm.invoice.api.resource.InvoiceRegistrationResource;
import tigers.cave.webm.invoice.api.resource.InvoiceRegistrationResultResource;
import tigers.cave.webm.invoice.api.resource.query.InvoiceListResourceQuery;
import tigers.cave.webm.invoice.api.service.InvoiceService;
import tigers.cave.webm.invoice.dao.repository.criteria.InvoiceCriteria;

// TODO: 自動生成された Javadoc
/**
 * The Class InvoiceApiController.
 */
@RestController
@RequestMapping("api/invoices")
public class InvoiceApiController {

	/** The invoice service. */
	@Autowired
	InvoiceService invoiceService;

	/**
	 * Search invoices.
	 *
	 * @param uriBuilder the uri builder
	 * @return the invoice list resource
	 */
	@RequestMapping(method = RequestMethod.GET)
	public InvoiceListResource searchInvoices(
			@Validated InvoiceListResourceQuery invoiceListResourceQuery,
			UriComponentsBuilder uriBuilder) {

		//TODO 設定処理　serviceに移す？
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		InvoiceCriteria invoiceCriteria = new InvoiceCriteria();

		try {

			if (!StringUtils.isEmpty(invoiceListResourceQuery.getStart())) {
				invoiceCriteria.setStart(Integer.parseInt(invoiceListResourceQuery.getStart())-1);
			}

			if (!StringUtils.isEmpty(invoiceListResourceQuery.getMaxCount())) {
				invoiceCriteria.setMaxCount(Integer.parseInt(invoiceListResourceQuery.getMaxCount()));
			}

			if (!StringUtils.isEmpty(invoiceListResourceQuery.getClientNo())) {
				invoiceCriteria.setClientNo(Integer.parseInt(invoiceListResourceQuery.getClientNo()));
			}

			if (!StringUtils.isEmpty(invoiceListResourceQuery.getInvoiceStatus())) {
				invoiceCriteria.setInvoiceStatus(invoiceListResourceQuery.getInvoiceStatus());
			}

			if (!StringUtils.isEmpty(invoiceListResourceQuery.getInvoiceDateMin())) {

				invoiceCriteria.setInvoiceDateMin(dateFormat.parse(invoiceListResourceQuery.getInvoiceDateMin()));

			}

			if (!StringUtils.isEmpty(invoiceListResourceQuery.getInvoiceDateMax())) {
				invoiceCriteria.setInvoiceDateMax(dateFormat.parse(invoiceListResourceQuery.getInvoiceDateMax()));
			}

		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		InvoiceListResource invoiceListResource = invoiceService.findAllInvoicesByCriteria(invoiceCriteria, uriBuilder);

		return invoiceListResource;
	}

	/**
	 * Gets the invoice.
	 *
	 * @param invoiceNo the invoice no
	 * @return the invoice
	 */
	@RequestMapping(path = "{invoiceNo}", method = RequestMethod.GET)
	public InvoiceDetailResource getInvoice(@PathVariable String invoiceNo) {

		//TODO　以下実装修正する
		InvoiceDetailResource invoiceDetailResource = invoiceService.findInvoice(invoiceNo);

		return invoiceDetailResource;
	}

	/**
	 * Creates the invoice.
	 *
	 * @param newInvoice the new invoice
	 * @return the invoice registration result resource
	 */
	@RequestMapping(method = RequestMethod.POST)
	public InvoiceRegistrationResultResource createInvoice(
			@Validated @RequestBody InvoiceRegistrationResource newInvoice,
			UriComponentsBuilder uriBuilder) {

		InvoiceRegistrationResultResource invoiceRegistrationResultResource = invoiceService.createInvoice(newInvoice,
				uriBuilder);

		return invoiceRegistrationResultResource;
	}

}
