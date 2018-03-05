package tigers.cave.webm.invoice.api.service;

import java.math.BigDecimal;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import tigers.cave.webm.invoice.api.common.ApiProperties;
import tigers.cave.webm.invoice.api.common.ApplicationException;
import tigers.cave.webm.invoice.api.common.constant.MessageDetailType;
import tigers.cave.webm.invoice.api.common.constant.MessageType;
import tigers.cave.webm.invoice.api.controller.InvoiceApiController;
import tigers.cave.webm.invoice.api.resource.InvoiceDetailResource;
import tigers.cave.webm.invoice.api.resource.InvoiceListResource;
import tigers.cave.webm.invoice.api.resource.InvoiceRegistrationResource;
import tigers.cave.webm.invoice.api.resource.InvoiceRegistrationResultResource;
import tigers.cave.webm.invoice.api.resource.InvoiceResource;
import tigers.cave.webm.invoice.api.resource.OrderResource;
import tigers.cave.webm.invoice.api.resource.query.InvoiceListResourceQuery;
import tigers.cave.webm.invoice.dao.common.constant.DelFlg;
import tigers.cave.webm.invoice.dao.common.constant.InvoiceStatus;
import tigers.cave.webm.invoice.dao.common.constant.ItemType;
import tigers.cave.webm.invoice.dao.model.Client;
import tigers.cave.webm.invoice.dao.model.Invoice;
import tigers.cave.webm.invoice.dao.model.Order;
import tigers.cave.webm.invoice.dao.repository.ClientRepository;
import tigers.cave.webm.invoice.dao.repository.InvoiceRepository;
import tigers.cave.webm.invoice.dao.repository.OrderRepository;
import tigers.cave.webm.invoice.dao.repository.criteria.InvoiceCriteria;

/**
 * The Class InvoiceService.
 */
@Service
public class InvoiceService {

  /** The invoice repository. */
  @Autowired
  InvoiceRepository invoiceRepository;

  /** The client repository. */
  @Autowired
  ClientRepository clientRepository;

  /** The order repository. */
  @Autowired
  OrderRepository orderRepository;

  /** The api properties. */
  @Autowired
  ApiProperties apiProperties;

  /**
   * Find invoice.
   *
   * @param invoiceNo the invoice no
   * @return the invoice detail resource
   * @throws ApplicationException the application exception
   */
  @Transactional(readOnly = true)
  public InvoiceDetailResource findInvoice(String invoiceNo) throws ApplicationException {

    //請求書データ取得
    Invoice invoice = invoiceRepository.findByInvoiceNo(Integer.parseInt(invoiceNo),
        DelFlg.NOT_DELETE.getValue());

    //請求書データを取得できない場合はApplicationExceptionを返却
    if (invoice == null) {

      ApplicationException ae = new ApplicationException(MessageType.BAD_REQUEST.getCode(),
          new String[] {});
      ae.addDetailList(MessageDetailType.INVOICE_NOT_EXIST.getDetailCode(), new String[] {}, "");

      throw ae;

    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(apiProperties.getDateFormat());
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(apiProperties.getDateTimeFormat());

    InvoiceDetailResource invoiceDetailResource = new InvoiceDetailResource();
    Client client = invoice.getClientTbl();

    //請求書データをResouceクラスに設定
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
    invoiceDetailResource
        .setInvoiceStatus(InvoiceStatus.decode(invoice.getInvoiceStatus()).getName());
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
        simpleDateFormat2.format(invoice.getCreateDatetime()));
    invoiceDetailResource.setUpdateUser(invoice.getUpdateUser());
    invoiceDetailResource.setUpdateDatetime(
        simpleDateFormat2.format(invoice.getUpdateDatetime()));

    //請求書に紐づく注文実績データ取得
    List<Order> orderList = orderRepository.findByClientNoAndInvoiceTerm(
        client.getClientNo(),
        invoice.getInvoiceStartDate(),
        invoice.getInvoiceEndDate(),
        DelFlg.NOT_DELETE.getValue());

    //注文実績データをResourceクラスに設定
    List<OrderResource> orderResourceList = new ArrayList<OrderResource>();
    for (Order order : orderList) {

      OrderResource orderResource = new OrderResource();
      orderResource.setItemNo(String.valueOf(order.getItemNo()));
      orderResource.setItemName(order.getItemName());
      orderResource.setItemTypeCode(order.getItemType());
      orderResource.setItemType(ItemType.decode(order.getItemType()).getName());
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

  /**
   * Find all invoices by criteria.
   *
   * @param invoiceListResourceQuery the invoice list resource query
   * @param uriBuilder the uri builder
   * @return the invoice list resource
   * @throws Exception the exception
   */
  @Transactional(readOnly = true)
  public InvoiceListResource findAllInvoicesByCriteria(
      InvoiceListResourceQuery invoiceListResourceQuery,
      UriComponentsBuilder uriBuilder) throws Exception {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(apiProperties.getDateFormat());
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(apiProperties.getDateTimeFormat());
    InvoiceCriteria invoiceCriteria = new InvoiceCriteria();

    String start = apiProperties.getStartDefaultValue();

    //絞り込み条件の設定
    if (!StringUtils.isEmpty(invoiceListResourceQuery.getStart())) {
      start = invoiceListResourceQuery.getStart();
      invoiceCriteria.setStart(Integer.parseInt(start) - 1);
    } else {
      invoiceCriteria.setStart(Integer.parseInt(start) - 1);
    }

    if (!StringUtils.isEmpty(invoiceListResourceQuery.getMaxCount())) {
      invoiceCriteria.setMaxCount(Integer.parseInt(invoiceListResourceQuery.getMaxCount()));
    } else {
      invoiceCriteria.setMaxCount(Integer.parseInt(apiProperties.getMaxCountDefaultValue()));
    }

    if (!StringUtils.isEmpty(invoiceListResourceQuery.getClientNo())) {
      invoiceCriteria.setClientNo(Integer.parseInt(invoiceListResourceQuery.getClientNo()));
    }

    if (!StringUtils.isEmpty(invoiceListResourceQuery.getInvoiceStatus())) {
      invoiceCriteria.setInvoiceStatus(invoiceListResourceQuery.getInvoiceStatus());
    }

    if (!StringUtils.isEmpty(invoiceListResourceQuery.getInvoiceDateMin())) {

      invoiceCriteria
          .setInvoiceDateMin(simpleDateFormat.parse(invoiceListResourceQuery.getInvoiceDateMin()));

    }

    if (!StringUtils.isEmpty(invoiceListResourceQuery.getInvoiceDateMax())) {
      invoiceCriteria
          .setInvoiceDateMax(simpleDateFormat.parse(invoiceListResourceQuery.getInvoiceDateMax()));
    }

    //請求書データの検索結果取得
    List<Invoice> invoiceList = invoiceRepository.findByCriteria(invoiceCriteria);

    //請求書データをResourceクラスに設定
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
      invoiceResource.setInvoiceStatus(InvoiceStatus.decode(invoice.getInvoiceStatus()).getName());
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
          simpleDateFormat2.format(invoice.getCreateDatetime()));
      invoiceResource.setUpdateUser(invoice.getUpdateUser());
      invoiceResource.setUpdateDatetime(
          simpleDateFormat2.format(invoice.getUpdateDatetime()));

      URI resourceUri = getInvoiceUri(uriBuilder, String.valueOf(invoice.getInvoiceNo()));

      invoiceResource.setUrl(resourceUri.toString());

      invoiceResourceList.add(invoiceResource);

    }

    InvoiceListResource invoiceListResource = new InvoiceListResource();

    invoiceListResource.setInvoicesMaxCount(
        String.valueOf(invoiceRepository.countAllByCriteria(invoiceCriteria)));

    invoiceListResource.setStart(start);

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
   * @throws Exception the exception
   */
  @Transactional
  public InvoiceRegistrationResultResource createInvoice(InvoiceRegistrationResource newInvoice,
      UriComponentsBuilder uriBuilder) throws Exception {

    SimpleDateFormat sdf = new SimpleDateFormat(apiProperties.getDateFormat());
    Date invoiceStartDate = sdf.parse(newInvoice.getInvoiceStartDate());
    Date invoiceEndDate = sdf.parse(newInvoice.getInvoiceEndDate());

    //顧客管理データ取得
    Client client = clientRepository.findByClientNo(Integer.parseInt(newInvoice.getClientNo()),
        DelFlg.NOT_DELETE.getValue());

    //顧客管理データ存在チェック
    if (client == null) {

      ApplicationException ae = new ApplicationException(MessageType.BAD_REQUEST.getCode(),
          new String[] {});
      ae.addDetailList(MessageDetailType.NOT_EXIST.getDetailCode(), new String[] { "clientNo" },
          "clientNo");

      throw ae;

    }

    //請求書期間重複チェック
    List<Invoice> invoiceList = invoiceRepository.findByClientNoAndInvoiceTerm(
        Integer.parseInt(newInvoice.getClientNo()),
        invoiceStartDate,
        invoiceEndDate,
        DelFlg.NOT_DELETE.getValue());

    if (!invoiceList.isEmpty()) {

      ApplicationException ae = new ApplicationException(MessageType.BAD_REQUEST.getCode(),
          new String[] {});
      ae.addDetailList(MessageDetailType.INVOICE_DUPLICATION.getDetailCode(), new String[] {}, "");

      throw ae;

    }

    //注文実績データ取得
    List<Order> orderList = orderRepository.findByClientNoAndInvoiceTerm(
        Integer.parseInt(newInvoice.getClientNo()),
        invoiceStartDate,
        invoiceEndDate,
        DelFlg.NOT_DELETE.getValue());

    //注文実績データ存在チェック
    if (orderList.isEmpty()) {

      ApplicationException ae = new ApplicationException(MessageType.BAD_REQUEST.getCode(),
          new String[] {});
      ae.addDetailList(MessageDetailType.ORDER_NOT_EXIST.getDetailCode(), new String[] {}, "");

      throw ae;

    }

    Invoice invoice = new Invoice();
    Date today = new Date();

    //登録する請求書データ作成
    invoice.setClientTbl(client);
    invoice.setInvoiceStatus(InvoiceStatus.NEW.getCode());
    invoice.setInvoiceCreateDate(today);
    invoice.setInvoiceTitle(newInvoice.getInvoiceTitle());

    //請求金額計算
    BigDecimal invoiceAmt = BigDecimal.ZERO;
    for (Order order : orderList) {

      invoiceAmt = invoiceAmt.add(
          (new BigDecimal(order.getItemPrice())).multiply(
              new BigDecimal(order.getItemCount())));

    }
    invoice.setInvoiceAmt(invoiceAmt.intValue());

    //消費税計算
    invoice.setTaxAmt(
        invoiceAmt.multiply(new BigDecimal(apiProperties.getConsumptionTax())).intValue());

    invoice.setInvoiceStartDate(invoiceStartDate);
    invoice.setInvoiceEndDate(invoiceEndDate);
    invoice.setInvoiceNote(newInvoice.getInvoiceNote());
    invoice.setCreateUser(newInvoice.getCreateUser());
    invoice.setCreateDatetime(today);
    invoice.setUpdateUser(newInvoice.getCreateUser());
    invoice.setUpdateDatetime(today);
    invoice.setDelFlg(DelFlg.NOT_DELETE.getValue());

    //請求書データ登録
    invoiceRepository.save(invoice);

    //登録結果をResourceクラスに設定
    InvoiceRegistrationResultResource invoiceRegistrationResultResource = new InvoiceRegistrationResultResource();
    invoiceRegistrationResultResource.setInvoiceNo(String.valueOf(invoice.getInvoiceNo()));

    URI resourceUri = getInvoiceUri(uriBuilder, invoiceRegistrationResultResource.getInvoiceNo());

    invoiceRegistrationResultResource.setUrl(resourceUri.toString());

    return invoiceRegistrationResultResource;

  }

  /**
   * Gets the invoice uri.
   *
   * @param uriBuilder the uri builder
   * @param invoiceNo the invoice no
   * @return the invoice uri
   * @throws ApplicationException the application exception
   */
  private URI getInvoiceUri(UriComponentsBuilder uriBuilder, String invoiceNo)
      throws ApplicationException {

    return MvcUriComponentsBuilder.relativeTo(uriBuilder)
        .withMethodCall(
            MvcUriComponentsBuilder.on(InvoiceApiController.class).getInvoice(invoiceNo))
        .build().encode().toUri();
  }

}
