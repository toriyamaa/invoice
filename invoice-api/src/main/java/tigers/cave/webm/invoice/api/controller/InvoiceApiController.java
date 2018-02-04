package tigers.cave.webm.invoice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import tigers.cave.webm.invoice.api.validation.Numeric;

/**
 * The Class InvoiceApiController.
 */
@RestController
@RequestMapping("api/invoices")
@Validated
public class InvoiceApiController {

	/** The invoice service. */
	@Autowired
	InvoiceService invoiceService;

	/**
	 * Search invoices.
	 *
	 * @param invoiceListResourceQuery the invoice list resource query
	 * @param uriBuilder the uri builder
	 * @return the invoice list resource
	 */
	@RequestMapping(method = RequestMethod.GET)
	public InvoiceListResource searchInvoices(
			@Validated InvoiceListResourceQuery invoiceListResourceQuery,
			UriComponentsBuilder uriBuilder) {

		InvoiceListResource invoiceListResource = invoiceService.findAllInvoicesByCriteria(invoiceListResourceQuery,
				uriBuilder);

		return invoiceListResource;
	}

	/**
	 * Gets the invoice.
	 *
	 * @param invoiceNo the invoice no
	 * @return the invoice
	 */
	@RequestMapping(path = "{invoiceNo}", method = RequestMethod.GET)
	public InvoiceDetailResource getInvoice(@PathVariable @Validated @Numeric(message = "NotNumber") String invoiceNo) {

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
