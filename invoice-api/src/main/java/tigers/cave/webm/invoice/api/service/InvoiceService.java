package tigers.cave.webm.invoice.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tigers.cave.webm.invoice.api.resource.InvoiceDetailResource;
import tigers.cave.webm.invoice.api.resource.InvoiceRegistrationResource;
import tigers.cave.webm.invoice.api.resource.InvoiceRegistrationResultResource;
import tigers.cave.webm.invoice.api.resource.OrderResource;
import tigers.cave.webm.invoice.dao.model.Client;
import tigers.cave.webm.invoice.dao.model.Invoice;
import tigers.cave.webm.invoice.dao.model.Order;
import tigers.cave.webm.invoice.dao.repository.ClientRepository;
import tigers.cave.webm.invoice.dao.repository.InvoiceRepository;
import tigers.cave.webm.invoice.dao.repository.OrderRepository;

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

		//TODO
		List<Order> orderList = orderRepository.findAll();
		List<OrderResource> orderResourceList = new ArrayList<OrderResource>();
		for(Order order: orderList) {


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
	public List<Invoice> findAllInvoicesByCriteria() {

		List<Invoice> list = invoiceRepository.findAll();
		return list;
	}

	@Transactional
	public InvoiceRegistrationResultResource createInvoice(InvoiceRegistrationResource newInvoice) {

		Invoice invoice = new Invoice();

		//TODO
		Date today = new Date();

		//TODO
		Client client = clientRepository.findOne(Integer.parseInt(newInvoice.getClientNo()));
		invoice.setClientTbl(client);
		invoice.setInvoiceStatus("10");
		invoice.setInvoiceCreateDate(today);

		invoice.setInvoiceTitle(newInvoice.getInvoiceTitle());

		//TODO
		invoice.setInvoiceAmt(200);
		invoice.setTaxAmt(216);

		//TODO
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			invoice.setInvoiceStartDate(sdf.parse(newInvoice.getInvoiceStartDate()));
			invoice.setInvoiceEndDate(sdf.parse(newInvoice.getInvoiceEndDate()));
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

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

		return invoiceRegistrationResultResource;

	}

}
