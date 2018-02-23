package tigers.cave.webm.invoice.api.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tigers.cave.webm.invoice.api.common.ApiProperties;
import tigers.cave.webm.invoice.api.common.ApplicationException;
import tigers.cave.webm.invoice.api.resource.InvoiceDetailResource;
import tigers.cave.webm.invoice.api.resource.OrderResource;
import tigers.cave.webm.invoice.dao.model.Client;
import tigers.cave.webm.invoice.dao.model.Invoice;
import tigers.cave.webm.invoice.dao.model.Order;
import tigers.cave.webm.invoice.dao.repository.InvoiceRepository;
import tigers.cave.webm.invoice.dao.repository.OrderRepository;

/**
 * The Class InvoiceServiceTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceTest {

  /** The service. */
  @InjectMocks
  InvoiceService service;

  /** The invoice repository. */
  @Mock
  InvoiceRepository invoiceRepository;

  /** The order repository. */
  @Mock
  OrderRepository orderRepository;

  /** The api properties. */
  @Mock
  ApiProperties apiProperties;

  /** The Constant INVOICE_NO. */
  private static final int DEFAULT_INVOICE_NO = 10000;

  /** The Constant CLIENT_NO. */
  private static final int DEFAULTCLIENT_NO = 1000;

  /** The Constant CLIENT_CHARGE_LAST_NAME. */
  private static final String DEFAULT_CLIENT_CHARGE_LAST_NAME = "LastName";

  /** The Constant CHARGE_FIRST_NAME. */
  private static final String DEFAULT_CHARGE_FIRST_NAME = "FirstName";

  /** The Constant CLIENT_NAME. */
  private static final String DEFAULT_CLIENT_NAME = "ClientName";

  /** The Constant CLIENT_ADDRESS. */
  private static final String DEFAULT_CLIENT_ADDRESS = "ClientAddress";

  /** The Constant CLIENT_TEL. */
  private static final String DEFAULT_CLIENT_TEL = "0000-000-000";

  /** The Constant CLIENT_FAX. */
  private static final String DEFAULT_CLIENT_FAX = "1111-111-111";

  /** The Constant INVOICE_STATUS. */
  private static final String DEFAULT_INVOICE_STATUS = "10";

  /** The Constant INVOICE_TITLE. */
  private static final String DEFAULT_INVOICE_TITLE = "testInvoiceTitle";

  /** The Constant INVOICE_NOTE. */
  private static final String DEFAULT_INVOICE_NOTE = "testInvoiceNote";

  /** The Constant CREATE_USER. */
  private static final String DEFAULT_CREATE_USER = "testCreateUser";

  /** The Constant UPDATE_USER. */
  private static final String DEFAULT_UPDATE_USER = "testUpdateUser";

  /** The Constant INVOICE_AMT. */
  private static final int DEFAULT_INVOICE_AMT = 100;

  /** The Constant TAX_AMT. */
  private static final int DEFAULT_TAX_AMT = 108;

  /** The Constant DEFAULT_ITEM_NO. */
  private static final int DEFAULT_ITEM_NO = 500000;

  /** The Constant DEFAULT_ITEM_NAME. */
  private static final String DEFAULT_ITEM_NAME = "itemName";

  /** The Constant DEFAULT_ITEM_TYPE. */
  private static final String DEFAULT_ITEM_TYPE = "10";

  /** The Constant DEFAULT_ITEM_PRICE. */
  private static final int DEFAULT_ITEM_PRICE = 500;

  /** The Constant DEFAULT_ITEM_COUNT. */
  private static final int DEFAULT_ITEM_COUNT = 10;

  /**
   * 請求書データ取得失敗時のエラーハンドリング
   *
   * 以下の値を持つApplicationExceptionが返却されること
   * ・code：InvoiceNotExist
   * ・messageOption：new String[] {}
   * ・field："".
   */
  @Test
  public void testErrCheckFindInvoice() {

    doReturn(null).when(invoiceRepository).findByInvoiceNo(1000, "0");

    try {
      service.findInvoice("1000");
    } catch (ApplicationException e) {

      assertThat(e.getDetailList().size(), is(1));
      assertThat(e.getDetailList().get(0).getCode(), is("InvoiceNotExist"));
      assertThat(e.getDetailList().get(0).getMessageOption(), is(new String[] {}));
      assertThat(e.getDetailList().get(0).getField(), is(""));

    }
  }

  /**
   * 請求書取得レスポンス確認
   * ※注文実績の一覧が0件の場合.
   */
  @Test
  public void testFindInvoice() {

    //モック用Clientデータ作成
    Client testClient = getDefaultClient();

    //モック用Invoiceデータ作成
    Invoice testInvoice = getDefaultInvoice();
    testInvoice.setClientTbl(testClient);

    //モック作成
    doReturn(testInvoice).when(invoiceRepository).findByInvoiceNo(10000, "0");
    doReturn("yyyy-MM-dd").when(apiProperties).getDateFormat();
    doReturn("yyyy-MM-dd'T'HH:mm:ss.SSSZ").when(apiProperties).getDateTimeFormat();
    doReturn(new ArrayList<Order>()).when(orderRepository).findByClientNoAndInvoiceTerm(
        1000,
        Date.from(
            (LocalDate.of(2018, 1, 2).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        Date.from(
            (LocalDate.of(2018, 1, 3).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        "0");

    //テスト期待値作成
    InvoiceDetailResource expectedInvoiceDetailResource = new InvoiceDetailResource();
    expectedInvoiceDetailResource.setInvoiceNo(String.valueOf(DEFAULT_INVOICE_NO));
    expectedInvoiceDetailResource.setClientNo(String.valueOf(DEFAULTCLIENT_NO));
    expectedInvoiceDetailResource.setClientChargeName(DEFAULT_CLIENT_CHARGE_LAST_NAME + DEFAULT_CHARGE_FIRST_NAME);
    expectedInvoiceDetailResource.setClientName(DEFAULT_CLIENT_NAME);
    expectedInvoiceDetailResource.setClientAddress(DEFAULT_CLIENT_ADDRESS);
    expectedInvoiceDetailResource.setClientTel(DEFAULT_CLIENT_TEL);
    expectedInvoiceDetailResource.setClientFax(DEFAULT_CLIENT_FAX);
    expectedInvoiceDetailResource.setInvoiceStatusCode(DEFAULT_INVOICE_STATUS);
    expectedInvoiceDetailResource.setInvoiceStatus("新規作成");
    expectedInvoiceDetailResource.setInvoiceCreateDate("2018-01-01");
    expectedInvoiceDetailResource.setInvoiceTitle(DEFAULT_INVOICE_TITLE);
    expectedInvoiceDetailResource.setInvoiceAmt(String.valueOf(DEFAULT_INVOICE_AMT));
    expectedInvoiceDetailResource.setTaxAmt(String.valueOf(DEFAULT_TAX_AMT));
    expectedInvoiceDetailResource.setInvoiceStartDate("2018-01-02");
    expectedInvoiceDetailResource.setInvoiceEndDate("2018-01-03");
    expectedInvoiceDetailResource.setInvoiceNote(DEFAULT_INVOICE_NOTE);
    expectedInvoiceDetailResource.setCreateUser(DEFAULT_CREATE_USER);
    expectedInvoiceDetailResource.setCreateDatetime("2018-01-04");
    expectedInvoiceDetailResource.setUpdateUser(DEFAULT_UPDATE_USER);
    expectedInvoiceDetailResource.setUpdateDatetime("2018-01-05");
    expectedInvoiceDetailResource.setOrdersCount("0");
    expectedInvoiceDetailResource.setOrders(new ArrayList<OrderResource>());

    try {
      assertThat(service.findInvoice("10000"), is(samePropertyValuesAs(expectedInvoiceDetailResource)));
    } catch (ApplicationException e) {
      fail("ERROR");
    }
  }

  /**
   * 請求書取得レスポンス確認
   * ※注文実績の一覧が複数件の場合.
   */
  @Test
  public void testFindInvoice2() {

    //モック用Clientデータ作成
    Client testClient = getDefaultClient();

    //モック用Invoiceデータ作成
    Invoice testInvoice = getDefaultInvoice();
    testInvoice.setClientTbl(testClient);

    //モック用Orderデータ作成
    List<Order> testOrderList = getDefaultOrderList(2);

    //モック作成
    doReturn(testInvoice).when(invoiceRepository).findByInvoiceNo(10000, "0");
    doReturn("yyyy-MM-dd").when(apiProperties).getDateFormat();
    doReturn("yyyy-MM-dd'T'HH:mm:ss.SSSZ").when(apiProperties).getDateTimeFormat();
    doReturn(testOrderList).when(orderRepository).findByClientNoAndInvoiceTerm(
        1000,
        Date.from(
            (LocalDate.of(2018, 1, 2).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        Date.from(
            (LocalDate.of(2018, 1, 3).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        "0");

    //テスト期待値作成
    InvoiceDetailResource expectedInvoiceDetailResource = new InvoiceDetailResource();
    expectedInvoiceDetailResource.setInvoiceNo(String.valueOf(DEFAULT_INVOICE_NO));
    expectedInvoiceDetailResource.setClientNo(String.valueOf(DEFAULTCLIENT_NO));
    expectedInvoiceDetailResource.setClientChargeName(DEFAULT_CLIENT_CHARGE_LAST_NAME + DEFAULT_CHARGE_FIRST_NAME);
    expectedInvoiceDetailResource.setClientName(DEFAULT_CLIENT_NAME);
    expectedInvoiceDetailResource.setClientAddress(DEFAULT_CLIENT_ADDRESS);
    expectedInvoiceDetailResource.setClientTel(DEFAULT_CLIENT_TEL);
    expectedInvoiceDetailResource.setClientFax(DEFAULT_CLIENT_FAX);
    expectedInvoiceDetailResource.setInvoiceStatusCode(DEFAULT_INVOICE_STATUS);
    expectedInvoiceDetailResource.setInvoiceStatus("新規作成");
    expectedInvoiceDetailResource.setInvoiceCreateDate("2018-01-01");
    expectedInvoiceDetailResource.setInvoiceTitle(DEFAULT_INVOICE_TITLE);
    expectedInvoiceDetailResource.setInvoiceAmt(String.valueOf(DEFAULT_INVOICE_AMT));
    expectedInvoiceDetailResource.setTaxAmt(String.valueOf(DEFAULT_TAX_AMT));
    expectedInvoiceDetailResource.setInvoiceStartDate("2018-01-02");
    expectedInvoiceDetailResource.setInvoiceEndDate("2018-01-03");
    expectedInvoiceDetailResource.setInvoiceNote(DEFAULT_INVOICE_NOTE);
    expectedInvoiceDetailResource.setCreateUser(DEFAULT_CREATE_USER);
    expectedInvoiceDetailResource.setCreateDatetime("2018-01-04");
    expectedInvoiceDetailResource.setUpdateUser(DEFAULT_UPDATE_USER);
    expectedInvoiceDetailResource.setUpdateDatetime("2018-01-05");
    expectedInvoiceDetailResource.setOrdersCount("2");

    OrderResource orderResource = new OrderResource();
    orderResource.setItemNo("500000");
    orderResource.setItemName("itemName");
    orderResource.setItemTypeCode("10");
    orderResource.setItemType("書籍");
    orderResource.setItemPrice("500");
    orderResource.setItemCount("10");
    orderResource.setCreateDatetime("2018-01-06T00:00:00.000+0900");
    orderResource.setUpdateDatetime("2018-01-07T00:00:00.000+0900");

    OrderResource orderResource2 = new OrderResource();
    orderResource2.setItemNo("500001");
    orderResource2.setItemName("itemName");
    orderResource2.setItemTypeCode("10");
    orderResource2.setItemType("書籍");
    orderResource2.setItemPrice("500");
    orderResource2.setItemCount("10");
    orderResource2.setCreateDatetime("2018-01-06T00:00:00.000+0900");
    orderResource2.setUpdateDatetime("2018-01-07T00:00:00.000+0900");

    List<OrderResource> orderResourceList = new ArrayList<OrderResource>();
    orderResourceList.add(orderResource);
    orderResourceList.add(orderResource2);
    expectedInvoiceDetailResource.setOrders(orderResourceList);

    try {
      assertThat(service.findInvoice("10000"), is(samePropertyValuesAs(expectedInvoiceDetailResource)));
    } catch (ApplicationException e) {
      fail("ERROR");
    }
  }

  /**
   * デフォルトのClientデータ.
   *
   * @return the default client
   */
  private Client getDefaultClient() {

    Client defaultClient = new Client();
    defaultClient.setClientNo(DEFAULTCLIENT_NO);
    defaultClient.setClientChargeLastName(DEFAULT_CLIENT_CHARGE_LAST_NAME);
    defaultClient.setClientChargeFirstName(DEFAULT_CHARGE_FIRST_NAME);
    defaultClient.setClientName(DEFAULT_CLIENT_NAME);
    defaultClient.setClientAddress(DEFAULT_CLIENT_ADDRESS);
    defaultClient.setClientTel(DEFAULT_CLIENT_TEL);
    defaultClient.setClientFax(DEFAULT_CLIENT_FAX);

    return defaultClient;

  }

  /**
   * デフォルトのClientデータ.
   *
   * @return the default invoice
   */
  private Invoice getDefaultInvoice() {

    Invoice defaultInvoice = new Invoice();
    defaultInvoice.setInvoiceNo(DEFAULT_INVOICE_NO);
    defaultInvoice.setInvoiceStatus(DEFAULT_INVOICE_STATUS);

    defaultInvoice.setInvoiceCreateDate(
        Date.from(
            (LocalDate.of(2018, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())));

    defaultInvoice.setInvoiceTitle(DEFAULT_INVOICE_TITLE);
    defaultInvoice.setInvoiceAmt(DEFAULT_INVOICE_AMT);
    defaultInvoice.setTaxAmt(DEFAULT_TAX_AMT);

    defaultInvoice.setInvoiceStartDate(
        Date.from(
            (LocalDate.of(2018, 1, 2).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    defaultInvoice.setInvoiceEndDate(
        Date.from(
            (LocalDate.of(2018, 1, 3).atStartOfDay(ZoneId.systemDefault()).toInstant())));

    defaultInvoice.setInvoiceNote(DEFAULT_INVOICE_NOTE);
    defaultInvoice.setCreateUser(DEFAULT_CREATE_USER);

    defaultInvoice.setCreateDatetime(
        Date.from(
            (LocalDate.of(2018, 1, 4).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    defaultInvoice.setUpdateUser(DEFAULT_UPDATE_USER);

    defaultInvoice.setUpdateDatetime(
        Date.from(
            (LocalDate.of(2018, 1, 5).atStartOfDay(ZoneId.systemDefault()).toInstant())));

    return defaultInvoice;

  }

  /**
   * デフォルトのOrderデータの一覧.
   *
   * @param count the count
   * @return the default order list
   */
  private List<Order> getDefaultOrderList(int count) {

    List<Order> orderList = new ArrayList<Order>();

    for(int i = 0; i < count; i++) {

      Order order = getDefaultOrder();
      order.setItemNo(order.getItemNo()+i);

      orderList.add(order);

    }

    return orderList;

  }

  /**
   * デフォルトのOrderデータ.
   *
   * @return the default order
   */
  private Order getDefaultOrder() {

    Order defaultOrder = new Order();
    defaultOrder.setItemNo(DEFAULT_ITEM_NO);
    defaultOrder.setItemName(DEFAULT_ITEM_NAME);
    defaultOrder.setItemType(DEFAULT_ITEM_TYPE);
    defaultOrder.setItemPrice(DEFAULT_ITEM_PRICE);
    defaultOrder.setItemCount(DEFAULT_ITEM_COUNT);
    defaultOrder.setCreateDatetime(
        Date.from(
            (LocalDate.of(2018, 1, 6).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    defaultOrder.setUpdateDatetime(
        Date.from(
            (LocalDate.of(2018, 1, 7).atStartOfDay(ZoneId.systemDefault()).toInstant())));

    return defaultOrder;

  }

}
