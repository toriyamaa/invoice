package invoice.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import invoice.api.resource.InvoiceDetailResource;
import invoice.api.resource.InvoiceListResource;
import invoice.api.resource.InvoiceRegistrationResource;
import invoice.api.resource.InvoiceRegistrationResultResource;
import invoice.api.resource.InvoiceResource;
import invoice.api.resource.OrderResource;
import invoice.api.service.InvoiceService;

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
	 * @return the invoice list resource
	 */
	@RequestMapping(method = RequestMethod.GET)
	public InvoiceListResource searchInvoices() {

		//TODO　以下実装修正する
		invoiceService.findAllInvoicesByCriteria();

		InvoiceResource invoiceResource = new InvoiceResource();

		List<InvoiceResource> invoiceResourceList = new ArrayList<InvoiceResource>();
		invoiceResourceList.add(invoiceResource);
		invoiceResourceList.add(invoiceResource);

		InvoiceListResource invoiceListResource = new InvoiceListResource();
		invoiceListResource.setInvoicesCount(
				String.valueOf(invoiceResourceList.size()));
		invoiceListResource.setInvoices(invoiceResourceList);

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
		invoiceService.findInvoice(invoiceNo);

		OrderResource orderResource = new OrderResource();

		List<OrderResource> orderResourceList = new ArrayList<OrderResource>();
		orderResourceList.add(orderResource);
		orderResourceList.add(orderResource);

		InvoiceDetailResource invoiceDetailResource = new InvoiceDetailResource();
		invoiceDetailResource.setInvoiceNo(invoiceNo);
		invoiceDetailResource.setOrdersCount(
				String.valueOf(orderResourceList.size()));
		invoiceDetailResource.setOrders(orderResourceList);

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
			@Validated @RequestBody InvoiceRegistrationResource newInvoice) {

		invoiceService.createInvoice();

		InvoiceRegistrationResultResource invoiceRegistrationResultResource = new InvoiceRegistrationResultResource();

		return invoiceRegistrationResultResource;
	}

}
