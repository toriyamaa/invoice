package tigers.cave.webm.invoice.api.service;

import java.math.BigDecimal;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import tigers.cave.webm.invoice.api.controller.InvoiceApiController;
import tigers.cave.webm.invoice.api.resource.InvoiceDetailResource;
import tigers.cave.webm.invoice.api.resource.InvoiceListResource;
import tigers.cave.webm.invoice.api.resource.InvoiceRegistrationResource;
import tigers.cave.webm.invoice.api.resource.InvoiceRegistrationResultResource;
import tigers.cave.webm.invoice.api.resource.InvoiceResource;
import tigers.cave.webm.invoice.api.resource.OrderResource;
import tigers.cave.webm.invoice.dao.model.Client;
import tigers.cave.webm.invoice.dao.model.Invoice;
import tigers.cave.webm.invoice.dao.model.Order;
import tigers.cave.webm.invoice.dao.repository.ClientRepository;
import tigers.cave.webm.invoice.dao.repository.InvoiceRepository;
import tigers.cave.webm.invoice.dao.repository.OrderRepository;
import tigers.cave.webm.invoice.dao.repository.criteria.InvoiceCriteria;

@Service
public class InvoiceService {

	@Autowired
	InvoiceRepository invoiceRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	OrderRepository orderRepository;

	@Transactional(readOnly = true)
	public InvoiceDetailResource findInvoice(String invoiceNo) {

		//TODO delFlg設定
		Invoice invoice = invoiceRepository.findOne(Integer.parseInt(invoiceNo));
		if (invoice == null) {
			return new InvoiceDetailResource();
		}

		//TODO
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		InvoiceDetailResource invoiceDetailResource = new InvoiceDetailResource();
		Client client = invoice.getClientTbl();

		invoiceDetailResource.setInvoiceNo(invoiceNo);
		invoiceDetailResource.setClientNo(String.valueOf(client.getClientNo()));
		invoiceDetailResource.setClientChargeName(
				invoice.getClientTbl().getClientChargeLastName()
						+ client.getClientChargeFirstName());
		invoiceDetailResource.setClientName(client.getClientName());
		invoiceDetailResource.setClientAddress(client.getClientAddress());
		invoiceDetailResource.setClientTel(client.getClientTel());
		invoiceDetailResource.setClientFax(client.getClientFax());
		invoiceDetailResource.setInvoiceStatusCode(invoice.getInvoiceStatus());
		//TODO
		invoiceDetailResource.setInvoiceStatus("新規作成");
		invoiceDetailResource.setInvoiceCreateDate(
				simpleDateFormat.format(invoice.getInvoiceCreateDate()));
		invoiceDetailResource.setInvoiceTitle(invoice.getInvoiceTitle());
		invoiceDetailResource.setInvoiceAmt(String.valueOf(invoice.getInvoiceAmt()));
		invoiceDetailResource.setTaxAmt(String.valueOf(invoice.getTaxAmt()));
		invoiceDetailResource.setInvoiceStartDate(
				simpleDateFormat.format(invoice.getInvoiceStartDate()));
		invoiceDetailResource.setInvoiceEndDate(
				simpleDateFormat.format(invoice.getInvoiceEndDate()));
		invoiceDetailResource.setInvoiceNote(invoice.getInvoiceNote());
		invoiceDetailResource.setCreateUser(invoice.getCreateUser());
		invoiceDetailResource.setCreateDatetime(
				simpleDateFormat.format(invoice.getCreateDatetime()));
		invoiceDetailResource.setUpdateUser(invoice.getUpdateUser());
		invoiceDetailResource.setUpdateDatetime(
				simpleDateFormat.format(invoice.getUpdateDatetime()));

		////TODO
		List<Order> orderList = orderRepository.findByClientNoAndInvoiceTerm(client.getClientNo(),
				invoice.getInvoiceStartDate(),
				invoice.getInvoiceEndDate());

		List<OrderResource> orderResourceList = new ArrayList<OrderResource>();
		for (Order order : orderList) {

			OrderResource orderResource = new OrderResource();
			orderResource.setItemNo(String.valueOf(order.getItemNo()));
			orderResource.setItemName(order.getItemName());
			orderResource.setItemTypeCode(order.getItemType());

			//TODO
			orderResource.setItemType("書籍");

			orderResource.setItemPrice(String.valueOf(order.getItemPrice()));
			orderResource.setItemCount(String.valueOf(order.getItemCount()));
			orderResource.setCreateDatetime(
					simpleDateFormat2.format(order.getCreateDatetime()));
			orderResource.setUpdateDatetime(
					simpleDateFormat2.format(order.getUpdateDatetime()));

			orderResourceList.add(orderResource);
		}

		invoiceDetailResource.setOrdersCount(
				String.valueOf(orderResourceList.size()));
		invoiceDetailResource.setOrders(orderResourceList);

		return invoiceDetailResource;
	}

	@Transactional(readOnly = true)
	public InvoiceListResource findAllInvoicesByCriteria(InvoiceCriteria invoiceCriteria,
			UriComponentsBuilder uriBuilder) {

		List<Invoice> invoiceList = invoiceRepository.findByCriteria(invoiceCriteria);

		//TODO
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
					.withMethodCall(MvcUriComponentsBuilder.on(InvoiceApiController.class)
							.getInvoice(String.valueOf(invoice.getInvoiceNo())))
					.build().encode().toUri();

			invoiceResource.setUrl(resourceUri.toString());

			invoiceResourceList.add(invoiceResource);

		}

		InvoiceListResource invoiceListResource = new InvoiceListResource();

		//TODO 総件数取得する
		invoiceListResource.setInvoicesMaxCount(
				String.valueOf(invoiceResourceList.size()));
		//TODO 設定する
		invoiceListResource.setStart("1");

		invoiceListResource.setInvoicesCount(
				String.valueOf(invoiceResourceList.size()));
		invoiceListResource.setInvoices(invoiceResourceList);

		return invoiceListResource;
	}

	/**
	 * Creates the invoice.
	 *
	 * @param newInvoice the new invoice
	 * @param uriBuilder the uri builder
	 * @return the invoice registration result resource
	 */
	@Transactional
	public InvoiceRegistrationResultResource createInvoice(InvoiceRegistrationResource newInvoice,
			UriComponentsBuilder uriBuilder) {

		Invoice invoice = new Invoice();

		//TODO
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		Date invoiceStartDate = null;
		Date invoiceEndDate = null;
		try {
			invoiceStartDate = sdf.parse(newInvoice.getInvoiceStartDate());
			invoiceEndDate = sdf.parse(newInvoice.getInvoiceEndDate());
		} catch (ParseException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		//TODO
		Client client = clientRepository.findOne(Integer.parseInt(newInvoice.getClientNo()));
		invoice.setClientTbl(client);
		invoice.setInvoiceStatus("10");
		invoice.setInvoiceCreateDate(today);

		invoice.setInvoiceTitle(newInvoice.getInvoiceTitle());

		//TODO
		List<Order> orderList = orderRepository.findByClientNoAndInvoiceTerm(
				client.getClientNo(),
				invoiceStartDate,
				invoiceEndDate);

		//TODO intで大丈夫か？
		int invoiceAmt = 0;
		for (Order order : orderList) {

			invoiceAmt = invoiceAmt + (order.getItemPrice() * order.getItemCount());
		}
		invoice.setInvoiceAmt(invoiceAmt);

		BigDecimal taxAmt = BigDecimal.valueOf(invoiceAmt);
		invoice.setTaxAmt(taxAmt.multiply(new BigDecimal(1.08)).intValue());//TODO 消費税計算

		invoice.setInvoiceStartDate(invoiceStartDate);
		invoice.setInvoiceEndDate(invoiceEndDate);

		invoice.setInvoiceNote(newInvoice.getInvoiceNote());
		invoice.setCreateUser(newInvoice.getCreateUser());
		invoice.setCreateDatetime(today);
		invoice.setUpdateUser(newInvoice.getCreateUser());
		invoice.setUpdateDatetime(today);

		//TODO
		invoice.setDelFlg("0");

		invoiceRepository.save(invoice);

		InvoiceRegistrationResultResource invoiceRegistrationResultResource = new InvoiceRegistrationResultResource();
		invoiceRegistrationResultResource.setInvoiceNo(String.valueOf(invoice.getInvoiceNo()));

		URI resourceUri = MvcUriComponentsBuilder.relativeTo(uriBuilder)
				.withMethodCall(MvcUriComponentsBuilder.on(InvoiceApiController.class)
						.getInvoice(invoiceRegistrationResultResource.getInvoiceNo()))
				.build().encode().toUri();

		invoiceRegistrationResultResource.setUrl(resourceUri.toString());

		return invoiceRegistrationResultResource;

	}

}
