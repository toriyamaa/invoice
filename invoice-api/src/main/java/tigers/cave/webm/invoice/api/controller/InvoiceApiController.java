package tigers.cave.webm.invoice.api.controller;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import tigers.cave.webm.invoice.api.resource.InvoiceDetailResource;
import tigers.cave.webm.invoice.api.resource.InvoiceListResource;
import tigers.cave.webm.invoice.api.resource.InvoiceRegistrationResource;
import tigers.cave.webm.invoice.api.resource.InvoiceRegistrationResultResource;
import tigers.cave.webm.invoice.api.resource.InvoiceResource;
import tigers.cave.webm.invoice.api.resource.OrderResource;
import tigers.cave.webm.invoice.api.service.InvoiceService;
import tigers.cave.webm.invoice.dao.model.Invoice;

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

//	@RequestMapping(method = RequestMethod.GET)
//	public String searchInvoices(UriComponentsBuilder uriBuilder) {
//		return "test";
//	}


	/**
	 * Search invoices.
	 *
	 * @return the invoice list resource
	 */
	@RequestMapping(method = RequestMethod.GET)
	public InvoiceListResource searchInvoices(UriComponentsBuilder uriBuilder) {

		//TODO
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		//TODO　以下実装修正する
		List<Invoice> invoiceList = invoiceService.findAllInvoicesByCriteria();

		List<InvoiceResource> invoiceResourceList = new ArrayList<InvoiceResource>();
		for (Invoice invoice : invoiceList) {

			InvoiceResource invoiceResource = new InvoiceResource();
			invoiceResource.setInvoiceNo(String.valueOf(invoice.getInvoiceNo()));
			invoiceResource.setClientNo(String.valueOf(invoice.getClientTbl().getClientNo()));
			invoiceResource.setClientChargeName(
					invoice.getClientTbl().getClientChargeLastName()
							+ invoice.getClientTbl().getClientChargeFirstName());
			invoiceResource.setClientName(invoice.getClientTbl().getClientName());
			invoiceResource.setInvoiceStatusCode(invoice.getInvoiceStatus());
			//TODO
			invoiceResource.setInvoiceStatus("新規作成");
			invoiceResource.setInvoiceCreateDate(
					simpleDateFormat.format(invoice.getInvoiceCreateDate()));
			invoiceResource.setInvoiceTitle(invoice.getInvoiceTitle());
			invoiceResource.setInvoiceAmt(String.valueOf(invoice.getInvoiceAmt()));
			invoiceResource.setTaxAmt(String.valueOf(invoice.getTaxAmt()));
			invoiceResource.setInvoiceStartDate(
					simpleDateFormat.format(invoice.getInvoiceStartDate()));
			invoiceResource.setInvoiceEndDate(
					simpleDateFormat.format(invoice.getInvoiceEndDate()));
			invoiceResource.setInvoiceNote(invoice.getInvoiceNote());
			invoiceResource.setCreateUser(invoice.getCreateUser());
			invoiceResource.setCreateDatetime(
					simpleDateFormat.format(invoice.getCreateDatetime()));
			invoiceResource.setUpdateUser(invoice.getUpdateUser());
			invoiceResource.setUpdateDatetime(
					simpleDateFormat.format(invoice.getUpdateDatetime()));

			URI resourceUri = MvcUriComponentsBuilder.relativeTo(uriBuilder)
					.withMethodCall
							(MvcUriComponentsBuilder.on(InvoiceApiController.class).getInvoice(String.valueOf(invoice.getInvoiceNo())))
					.build().encode().toUri();

			invoiceResource.setUrl(resourceUri.toString());

			invoiceResourceList.add(invoiceResource);

		}

		InvoiceListResource invoiceListResource = new InvoiceListResource();

		//TODO
		invoiceListResource.setInvoicesMaxCount("2");
		invoiceListResource.setStart("1");

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
