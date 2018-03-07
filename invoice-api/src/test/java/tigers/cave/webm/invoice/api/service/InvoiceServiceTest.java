package tigers.cave.webm.invoice.api.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.util.UriComponentsBuilder;

import tigers.cave.webm.invoice.api.common.ApiProperties;
import tigers.cave.webm.invoice.api.common.ApplicationException;
import tigers.cave.webm.invoice.api.resource.InvoiceDetailResource;
import tigers.cave.webm.invoice.api.resource.InvoiceListResource;
import tigers.cave.webm.invoice.api.resource.InvoiceRegistrationResource;
import tigers.cave.webm.invoice.api.resource.InvoiceRegistrationResultResource;
import tigers.cave.webm.invoice.api.resource.InvoiceResource;
import tigers.cave.webm.invoice.api.resource.OrderResource;
import tigers.cave.webm.invoice.api.resource.query.InvoiceListResourceQuery;
import tigers.cave.webm.invoice.dao.model.Client;
import tigers.cave.webm.invoice.dao.model.Invoice;
import tigers.cave.webm.invoice.dao.model.Order;
import tigers.cave.webm.invoice.dao.repository.ClientRepository;
import tigers.cave.webm.invoice.dao.repository.InvoiceRepository;
import tigers.cave.webm.invoice.dao.repository.OrderRepository;
import tigers.cave.webm.invoice.dao.repository.criteria.InvoiceCriteria;

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

  /** The client repository. */
  @Mock
  ClientRepository clientRepository;

  /** The api properties. */
  @Mock
  ApiProperties apiProperties;

  /**
   * 【findInvoiceメソッドのテスト】
   * 請求書データ取得失敗時のエラーハンドリング
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
      fail("ERROR");
    } catch (ApplicationException e) {

      assertThat(e.getDetailList().size(), is(1));
      assertThat(e.getDetailList().get(0).getCode(), is("InvoiceNotExist"));
      assertThat(e.getDetailList().get(0).getMessageOption(), is(new String[] {}));
      assertThat(e.getDetailList().get(0).getField(), is(""));

    }
  }

  /**
   * 【findInvoiceメソッドのテスト】
   * 請求書取得レスポンス確認
   * ※注文実績の一覧が0件の場合.
   */
  @Test
  public void testFindInvoice() {

    //モック用Clientデータ作成
    Client mockClient = getDefaultClient();

    //モック用Invoiceデータ作成
    Invoice mockInvoice = getDefaultInvoice();
    mockInvoice.setClientTbl(mockClient);

    //モック作成
    doReturn(mockInvoice).when(invoiceRepository).findByInvoiceNo(10000, "0");
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
    expectedInvoiceDetailResource.setInvoiceNo(String.valueOf(10000));
    expectedInvoiceDetailResource.setClientNo(String.valueOf(1000));
    expectedInvoiceDetailResource.setClientChargeName("LastName" + "FirstName");
    expectedInvoiceDetailResource.setClientName("ClientName");
    expectedInvoiceDetailResource.setClientAddress("ClientAddress");
    expectedInvoiceDetailResource.setClientTel("0000-000-000");
    expectedInvoiceDetailResource.setClientFax("1111-111-111");
    expectedInvoiceDetailResource.setInvoiceStatusCode("10");
    expectedInvoiceDetailResource.setInvoiceStatus("新規作成");
    expectedInvoiceDetailResource.setInvoiceCreateDate("2018-01-01");
    expectedInvoiceDetailResource.setInvoiceTitle("testInvoiceTitle");
    expectedInvoiceDetailResource.setInvoiceAmt(String.valueOf(100));
    expectedInvoiceDetailResource.setTaxAmt(String.valueOf(108));
    expectedInvoiceDetailResource.setInvoiceStartDate("2018-01-02");
    expectedInvoiceDetailResource.setInvoiceEndDate("2018-01-03");
    expectedInvoiceDetailResource.setInvoiceNote("testInvoiceNote");
    expectedInvoiceDetailResource.setCreateUser("testCreateUser");
    expectedInvoiceDetailResource.setCreateDatetime("2018-01-04T00:00:00.000+0900");
    expectedInvoiceDetailResource.setUpdateUser("testUpdateUser");
    expectedInvoiceDetailResource.setUpdateDatetime("2018-01-05T00:00:00.000+0900");
    expectedInvoiceDetailResource.setOrdersCount("0");
    expectedInvoiceDetailResource.setOrders(new ArrayList<OrderResource>());

    try {
      assertThat(service.findInvoice("10000"),
          is(samePropertyValuesAs(expectedInvoiceDetailResource)));
    } catch (ApplicationException e) {
      fail("ERROR");
    }
  }

  /**
   * 【findInvoiceメソッドのテスト】
   * 請求書取得レスポンス確認
   * ※注文実績の一覧が複数件の場合.
   */
  @Test
  public void testFindInvoice2() {

    //モック用Clientデータ作成
    Client mockClient = getDefaultClient();

    //モック用Invoiceデータ作成
    Invoice mockInvoice = getDefaultInvoice();
    mockInvoice.setClientTbl(mockClient);

    //モック用Orderデータ作成
    List<Order> mockOrderList = getDefaultOrderList(2);

    //モック作成
    doReturn(mockInvoice).when(invoiceRepository).findByInvoiceNo(10000, "0");
    doReturn("yyyy-MM-dd").when(apiProperties).getDateFormat();
    doReturn("yyyy-MM-dd'T'HH:mm:ss.SSSZ").when(apiProperties).getDateTimeFormat();
    doReturn(mockOrderList).when(orderRepository).findByClientNoAndInvoiceTerm(
        1000,
        Date.from(
            (LocalDate.of(2018, 1, 2).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        Date.from(
            (LocalDate.of(2018, 1, 3).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        "0");

    //テスト期待値作成
    InvoiceDetailResource expectedInvoiceDetailResource = new InvoiceDetailResource();
    expectedInvoiceDetailResource.setInvoiceNo(String.valueOf(10000));
    expectedInvoiceDetailResource.setClientNo(String.valueOf(1000));
    expectedInvoiceDetailResource.setClientChargeName("LastName" + "FirstName");
    expectedInvoiceDetailResource.setClientName("ClientName");
    expectedInvoiceDetailResource.setClientAddress("ClientAddress");
    expectedInvoiceDetailResource.setClientTel("0000-000-000");
    expectedInvoiceDetailResource.setClientFax("1111-111-111");
    expectedInvoiceDetailResource.setInvoiceStatusCode("10");
    expectedInvoiceDetailResource.setInvoiceStatus("新規作成");
    expectedInvoiceDetailResource.setInvoiceCreateDate("2018-01-01");
    expectedInvoiceDetailResource.setInvoiceTitle("testInvoiceTitle");
    expectedInvoiceDetailResource.setInvoiceAmt(String.valueOf(100));
    expectedInvoiceDetailResource.setTaxAmt(String.valueOf(108));
    expectedInvoiceDetailResource.setInvoiceStartDate("2018-01-02");
    expectedInvoiceDetailResource.setInvoiceEndDate("2018-01-03");
    expectedInvoiceDetailResource.setInvoiceNote("testInvoiceNote");
    expectedInvoiceDetailResource.setCreateUser("testCreateUser");
    expectedInvoiceDetailResource.setCreateDatetime("2018-01-04T00:00:00.000+0900");
    expectedInvoiceDetailResource.setUpdateUser("testUpdateUser");
    expectedInvoiceDetailResource.setUpdateDatetime("2018-01-05T00:00:00.000+0900");
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
      assertThat(service.findInvoice("10000"),
          is(samePropertyValuesAs(expectedInvoiceDetailResource)));
    } catch (ApplicationException e) {
      fail("ERROR");
    }
  }

  /**
   * 【findAllInvoicesByCriteriaメソッドのテスト】
   *
   *請求書一覧取得の確認.
   *
   * [条件]
   * ・絞り込み条件なし
   *
   * [結果]
   * ・取得結果０件
   */
  @Test
  public void testFindAllInvoicesByCriteria() {

    //モック作成
    doReturn(new ArrayList<Invoice>()).when(invoiceRepository).findByCriteria(anyObject());
    doReturn(0L).when(invoiceRepository).countAllByCriteria(anyObject());
    doReturn("yyyy-MM-dd").when(apiProperties).getDateFormat();
    doReturn("yyyy-MM-dd'T'HH:mm:ss.SSSZ").when(apiProperties).getDateTimeFormat();
    doReturn("1").when(apiProperties).getStartDefaultValue();
    doReturn("10").when(apiProperties).getMaxCountDefaultValue();

    //テスト期待値作成
    InvoiceListResource expectedInvoiceListResource = new InvoiceListResource();
    expectedInvoiceListResource.setInvoicesMaxCount("0");
    expectedInvoiceListResource.setStart("1");
    expectedInvoiceListResource.setInvoicesCount("0");
    expectedInvoiceListResource.setInvoices(new ArrayList<InvoiceResource>());

    try {
      assertThat(
          service.findAllInvoicesByCriteria(
              new InvoiceListResourceQuery(),
              UriComponentsBuilder.newInstance()),
          is(samePropertyValuesAs(expectedInvoiceListResource)));
    } catch (Exception e) {
      fail("ERROR");
    }

  }

  /**
   * 【findAllInvoicesByCriteriaメソッドのテスト】
   *
   * 請求書一覧取得の確認.
   *
   * [条件]
   * ・絞り込み条件なし
   *
   * [結果]
   * ・取得結果2件
   */
  @Test
  public void testFindAllInvoicesByCriteria2() {

    //モック用Clientデータ作成
    Client mockClient = getDefaultClient();

    //モック用Invoiceデータ作成
    Invoice mockInvoice = getDefaultInvoice();
    Invoice mockInvoice2 = getDefaultInvoice();
    mockInvoice2.setInvoiceNo(mockInvoice.getInvoiceNo() + 1);
    mockInvoice.setClientTbl(mockClient);
    mockInvoice2.setClientTbl(mockClient);
    List<Invoice> mockInvoiceList = Arrays.asList(mockInvoice, mockInvoice2);

    //モック作成
    doReturn(mockInvoiceList).when(invoiceRepository).findByCriteria(anyObject());
    doReturn(2L).when(invoiceRepository).countAllByCriteria(anyObject());
    doReturn("yyyy-MM-dd").when(apiProperties).getDateFormat();
    doReturn("yyyy-MM-dd'T'HH:mm:ss.SSSZ").when(apiProperties).getDateTimeFormat();
    doReturn("1").when(apiProperties).getStartDefaultValue();
    doReturn("10").when(apiProperties).getMaxCountDefaultValue();

    //テスト期待値作成
    InvoiceListResource expectedInvoiceListResource = new InvoiceListResource();
    expectedInvoiceListResource.setInvoicesMaxCount("2");
    expectedInvoiceListResource.setStart("1");
    expectedInvoiceListResource.setInvoicesCount("2");

    InvoiceResource expectedInvoiceResource = new InvoiceResource();
    expectedInvoiceResource.setInvoiceNo("10000");
    expectedInvoiceResource.setClientNo("1000");
    expectedInvoiceResource.setClientChargeName("LastNameFirstName");
    expectedInvoiceResource.setClientName("ClientName");
    expectedInvoiceResource.setInvoiceStatusCode("10");
    expectedInvoiceResource.setInvoiceStatus("新規作成");
    expectedInvoiceResource.setInvoiceCreateDate("2018-01-01");
    expectedInvoiceResource.setInvoiceTitle("testInvoiceTitle");
    expectedInvoiceResource.setInvoiceAmt("100");
    expectedInvoiceResource.setTaxAmt("108");
    expectedInvoiceResource.setInvoiceStartDate("2018-01-02");
    expectedInvoiceResource.setInvoiceEndDate("2018-01-03");
    expectedInvoiceResource.setInvoiceNote("testInvoiceNote");
    expectedInvoiceResource.setCreateUser("testCreateUser");
    expectedInvoiceResource.setCreateDatetime("2018-01-04T00:00:00.000+0900");
    expectedInvoiceResource.setUpdateUser("testUpdateUser");
    expectedInvoiceResource.setUpdateDatetime("2018-01-05T00:00:00.000+0900");
    expectedInvoiceResource.setUrl("http://localhost:8080/api/invoices/10000");

    InvoiceResource expectedInvoiceResource2 = new InvoiceResource();
    expectedInvoiceResource2.setInvoiceNo("10001");
    expectedInvoiceResource2.setClientNo("1000");
    expectedInvoiceResource2.setClientChargeName("LastNameFirstName");
    expectedInvoiceResource2.setClientName("ClientName");
    expectedInvoiceResource2.setInvoiceStatusCode("10");
    expectedInvoiceResource2.setInvoiceStatus("新規作成");
    expectedInvoiceResource2.setInvoiceCreateDate("2018-01-01");
    expectedInvoiceResource2.setInvoiceTitle("testInvoiceTitle");
    expectedInvoiceResource2.setInvoiceAmt("100");
    expectedInvoiceResource2.setTaxAmt("108");
    expectedInvoiceResource2.setInvoiceStartDate("2018-01-02");
    expectedInvoiceResource2.setInvoiceEndDate("2018-01-03");
    expectedInvoiceResource2.setInvoiceNote("testInvoiceNote");
    expectedInvoiceResource2.setCreateUser("testCreateUser");
    expectedInvoiceResource2.setCreateDatetime("2018-01-04T00:00:00.000+0900");
    expectedInvoiceResource2.setUpdateUser("testUpdateUser");
    expectedInvoiceResource2.setUpdateDatetime("2018-01-05T00:00:00.000+0900");
    expectedInvoiceResource2.setUrl("http://localhost:8080/api/invoices/10001");

    List<InvoiceResource> expectedInvoiceResourceList = new ArrayList<InvoiceResource>();
    expectedInvoiceResourceList.add(expectedInvoiceResource);
    expectedInvoiceResourceList.add(expectedInvoiceResource2);

    expectedInvoiceListResource.setInvoices(expectedInvoiceResourceList);

    //findAllInvoicesByCriteriaメソッドの引数作成
    UriComponentsBuilder testUri = UriComponentsBuilder.newInstance();
    testUri.uri(URI.create("http://localhost:8080/"));

    try {

      assertThat(
          service.findAllInvoicesByCriteria(
              new InvoiceListResourceQuery(),
              testUri),
          is(samePropertyValuesAs(expectedInvoiceListResource)));

    } catch (Exception e) {
      fail("ERROR");
    }

  }

  /**
   * 【findAllInvoicesByCriteriaメソッドのテスト】
   *
   * 請求書一覧取得の確認.
   *
   * [条件]
   * ・絞り込み条件すべて指定
   *
   * [結果]
   * ・取得結果2件
   */
  @Test
  public void testFindAllInvoicesByCriteria3() {

    //モック用Clientデータ作成
    Client mockClient = getDefaultClient();

    //モック用Invoiceデータ作成
    Invoice mockInvoice = getDefaultInvoice();
    Invoice mockInvoice2 = getDefaultInvoice();
    mockInvoice2.setInvoiceNo(mockInvoice.getInvoiceNo() + 1);
    mockInvoice.setClientTbl(mockClient);
    mockInvoice2.setClientTbl(mockClient);

    //モックに渡す引数作成
    InvoiceCriteria mockInvoiceCriteria = new InvoiceCriteria();
    mockInvoiceCriteria.setStart(1);
    mockInvoiceCriteria.setMaxCount(2);
    mockInvoiceCriteria.setClientNo(1000);
    mockInvoiceCriteria.setInvoiceStatus("10");
    mockInvoiceCriteria.setInvoiceDateMin(
        Date.from(
            (LocalDate.of(2018, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    mockInvoiceCriteria.setInvoiceDateMax(
        Date.from(
            (LocalDate.of(2018, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())));

    //モック作成
    doReturn(Arrays.asList(mockInvoice, mockInvoice2))
        .when(invoiceRepository)
        .findByCriteria(mockInvoiceCriteria);
    doReturn(5L).when(invoiceRepository).countAllByCriteria(mockInvoiceCriteria);
    doReturn("yyyy-MM-dd").when(apiProperties).getDateFormat();
    doReturn("yyyy-MM-dd'T'HH:mm:ss.SSSZ").when(apiProperties).getDateTimeFormat();
    doReturn("1").when(apiProperties).getStartDefaultValue();
    doReturn("10").when(apiProperties).getMaxCountDefaultValue();

    //テスト期待値作成
    InvoiceListResource expectedInvoiceListResource = new InvoiceListResource();
    expectedInvoiceListResource.setInvoicesMaxCount("5");
    expectedInvoiceListResource.setStart("2");
    expectedInvoiceListResource.setInvoicesCount("2");

    InvoiceResource expectedInvoiceResource = new InvoiceResource();
    expectedInvoiceResource.setInvoiceNo("10000");
    expectedInvoiceResource.setClientNo("1000");
    expectedInvoiceResource.setClientChargeName("LastNameFirstName");
    expectedInvoiceResource.setClientName("ClientName");
    expectedInvoiceResource.setInvoiceStatusCode("10");
    expectedInvoiceResource.setInvoiceStatus("新規作成");
    expectedInvoiceResource.setInvoiceCreateDate("2018-01-01");
    expectedInvoiceResource.setInvoiceTitle("testInvoiceTitle");
    expectedInvoiceResource.setInvoiceAmt("100");
    expectedInvoiceResource.setTaxAmt("108");
    expectedInvoiceResource.setInvoiceStartDate("2018-01-02");
    expectedInvoiceResource.setInvoiceEndDate("2018-01-03");
    expectedInvoiceResource.setInvoiceNote("testInvoiceNote");
    expectedInvoiceResource.setCreateUser("testCreateUser");
    expectedInvoiceResource.setCreateDatetime("2018-01-04T00:00:00.000+0900");
    expectedInvoiceResource.setUpdateUser("testUpdateUser");
    expectedInvoiceResource.setUpdateDatetime("2018-01-05T00:00:00.000+0900");
    expectedInvoiceResource.setUrl("http://localhost:8080/api/invoices/10000");

    InvoiceResource expectedInvoiceResource2 = new InvoiceResource();
    expectedInvoiceResource2.setInvoiceNo("10001");
    expectedInvoiceResource2.setClientNo("1000");
    expectedInvoiceResource2.setClientChargeName("LastNameFirstName");
    expectedInvoiceResource2.setClientName("ClientName");
    expectedInvoiceResource2.setInvoiceStatusCode("10");
    expectedInvoiceResource2.setInvoiceStatus("新規作成");
    expectedInvoiceResource2.setInvoiceCreateDate("2018-01-01");
    expectedInvoiceResource2.setInvoiceTitle("testInvoiceTitle");
    expectedInvoiceResource2.setInvoiceAmt("100");
    expectedInvoiceResource2.setTaxAmt("108");
    expectedInvoiceResource2.setInvoiceStartDate("2018-01-02");
    expectedInvoiceResource2.setInvoiceEndDate("2018-01-03");
    expectedInvoiceResource2.setInvoiceNote("testInvoiceNote");
    expectedInvoiceResource2.setCreateUser("testCreateUser");
    expectedInvoiceResource2.setCreateDatetime("2018-01-04T00:00:00.000+0900");
    expectedInvoiceResource2.setUpdateUser("testUpdateUser");
    expectedInvoiceResource2.setUpdateDatetime("2018-01-05T00:00:00.000+0900");
    expectedInvoiceResource2.setUrl("http://localhost:8080/api/invoices/10001");

    List<InvoiceResource> expectedInvoiceResourceList = new ArrayList<InvoiceResource>();
    expectedInvoiceResourceList.add(expectedInvoiceResource);
    expectedInvoiceResourceList.add(expectedInvoiceResource2);

    expectedInvoiceListResource.setInvoices(expectedInvoiceResourceList);

    //findAllInvoicesByCriteriaメソッドの引数作成
    InvoiceListResourceQuery testQuery = new InvoiceListResourceQuery();
    testQuery.setStart("2");
    testQuery.setMaxCount("2");
    testQuery.setClientNo("1000");
    testQuery.setInvoiceStatus("10");
    testQuery.setInvoiceDateMin("2018-01-01");
    testQuery.setInvoiceDateMax("2018-02-01");

    UriComponentsBuilder testUri = UriComponentsBuilder.newInstance();
    testUri.uri(URI.create("http://localhost:8080/"));

    try {

      assertThat(
          service.findAllInvoicesByCriteria(
              testQuery,
              testUri),
          is(samePropertyValuesAs(expectedInvoiceListResource)));

    } catch (Exception e) {
      fail("ERROR");
    }

  }

  /**
   * 【createInvoiceメソッドのテスト】
   *
   * 請求書データ取得失敗時のエラーハンドリング
   *
   * 以下の値を持つApplicationExceptionが返却されること
   * ・NotExist
   * ・messageOption：new String[] {"clientNo"}
   * ・field：clientNo.
   */
  @Test
  public void testErrCheckCreateInvoice() {

    //モック作成
    doReturn(null).when(clientRepository).findByClientNo(1000, "0");
    doReturn("yyyy-MM-dd").when(apiProperties).getDateFormat();

    //createInvoiceメソッドの引数作成
    InvoiceRegistrationResource testNewInvoice = new InvoiceRegistrationResource();
    testNewInvoice.setClientNo("1000");
    testNewInvoice.setInvoiceStartDate("2018-01-01");
    testNewInvoice.setInvoiceEndDate("2018-02-01");
    testNewInvoice.setCreateUser("testCreateUser");

    UriComponentsBuilder testUri = UriComponentsBuilder.newInstance();
    testUri.uri(URI.create("http://localhost:8080/"));

    try {
      service.createInvoice(testNewInvoice, testUri);
      fail("ERROR");
    } catch (ApplicationException e) {

      assertThat(e.getDetailList().size(), is(1));
      assertThat(e.getDetailList().get(0).getCode(), is("NotExist"));
      assertThat(e.getDetailList().get(0).getMessageOption(), is(new String[] { "clientNo" }));
      assertThat(e.getDetailList().get(0).getField(), is("clientNo"));

    } catch (Exception e) {
      fail("ERROR(予期せぬ例外)");
    }
  }

  /**
   * 【createInvoiceメソッドのテスト】
   *
   * 請求書期間重複時のエラーハンドリング
   *
   * 以下の値を持つApplicationExceptionが返却されること
   * ・InvoiceDuplication
   * ・messageOption：new String[] {}
   * ・field："".
   */
  @Test
  public void testErrCheckCreateInvoice2() {

    //モック用Clientデータ作成
    Client mockClient = getDefaultClient();

    //モック用Invoiceデータ作成
    List<Invoice> mockInvoiceList = Arrays.asList(new Invoice());

    //モック作成
    doReturn(mockClient).when(clientRepository).findByClientNo(1000, "0");
    doReturn(mockInvoiceList).when(invoiceRepository).findByClientNoAndInvoiceTerm(
        1000,
        Date.from(
            (LocalDate.of(2018, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        Date.from(
            (LocalDate.of(2018, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        "0");
    doReturn("yyyy-MM-dd").when(apiProperties).getDateFormat();

    //createInvoiceメソッドの引数作成
    InvoiceRegistrationResource testNewInvoice = new InvoiceRegistrationResource();
    testNewInvoice.setClientNo("1000");
    testNewInvoice.setInvoiceStartDate("2018-01-01");
    testNewInvoice.setInvoiceEndDate("2018-02-01");
    testNewInvoice.setCreateUser("testCreateUser");

    UriComponentsBuilder testUri = UriComponentsBuilder.newInstance();
    testUri.uri(URI.create("http://localhost:8080/"));

    try {
      service.createInvoice(testNewInvoice, testUri);
      fail("ERROR");
    } catch (ApplicationException e) {

      assertThat(e.getDetailList().size(), is(1));
      assertThat(e.getDetailList().get(0).getCode(), is("InvoiceDuplication"));
      assertThat(e.getDetailList().get(0).getMessageOption(), is(new String[] {}));
      assertThat(e.getDetailList().get(0).getField(), is(""));

    } catch (Exception e) {
      fail("ERROR(予期せぬ例外)");
    }
  }

  /**
   * 【createInvoiceメソッドのテスト】
   *
   * 注文実績データ取得失敗時のエラーハンドリング
   *
   * 以下の値を持つApplicationExceptionが返却されること
   * ・OrderNotExist
   * ・messageOption：new String[] {}
   * ・field："".
   */
  @Test
  public void testErrCheckCreateInvoice3() {

    //モック用Clientデータ作成
    Client mockClient = getDefaultClient();

    //モック用Invoiceデータ作成
    List<Invoice> mockInvoiceList = new ArrayList<Invoice>();

    //モック用Orderデータ作成
    List<Order> mockOrderList = new ArrayList<Order>();

    //モック作成
    doReturn(mockClient).when(clientRepository).findByClientNo(1000, "0");
    doReturn(mockInvoiceList).when(invoiceRepository).findByClientNoAndInvoiceTerm(
        1000,
        Date.from(
            (LocalDate.of(2018, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        Date.from(
            (LocalDate.of(2018, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        "0");
    doReturn(mockOrderList).when(orderRepository).findByClientNoAndInvoiceTerm(
        1000,
        Date.from(
            (LocalDate.of(2018, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        Date.from(
            (LocalDate.of(2018, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        "0");
    doReturn("yyyy-MM-dd").when(apiProperties).getDateFormat();

    //createInvoiceメソッドの引数作成
    InvoiceRegistrationResource testNewInvoice = new InvoiceRegistrationResource();
    testNewInvoice.setClientNo("1000");
    testNewInvoice.setInvoiceStartDate("2018-01-01");
    testNewInvoice.setInvoiceEndDate("2018-02-01");
    testNewInvoice.setCreateUser("testCreateUser");

    UriComponentsBuilder testUri = UriComponentsBuilder.newInstance();
    testUri.uri(URI.create("http://localhost:8080/"));

    try {
      service.createInvoice(testNewInvoice, testUri);
      fail("ERROR");
    } catch (ApplicationException e) {

      assertThat(e.getDetailList().size(), is(1));
      assertThat(e.getDetailList().get(0).getCode(), is("OrderNotExist"));
      assertThat(e.getDetailList().get(0).getMessageOption(), is(new String[] {}));
      assertThat(e.getDetailList().get(0).getField(), is(""));

    } catch (Exception e) {
      fail("ERROR(予期せぬ例外)");
    }
  }

  /**
   * 【createInvoiceメソッドのテスト】
   *
   * 請求書データ登録の確認.
   *
   * [条件]
   * ・条件すべて指定
   *
   * [結果]
   * InvoiceRegistrationResultResourceの以下の項目が期待値であること
   * ・invoiceNo
   * ・url
   *
   */
  @Test
  public void testCreateInvoice() {

    //モック用Clientデータ作成
    Client mockClient = getDefaultClient();

    //モック用Invoiceデータ作成
    Invoice mockInvoice = getDefaultInvoice();
    mockInvoice.setClientTbl(mockClient);
    mockInvoice.setInvoiceStatus("10");
    mockInvoice.setInvoiceCreateDate(new Date());
    mockInvoice.setInvoiceTitle("title");
    mockInvoice.setInvoiceAmt(10000);
    mockInvoice.setTaxAmt(10800);
    mockInvoice.setInvoiceStartDate(
        Date.from(
            (LocalDate.of(2018, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    mockInvoice.setInvoiceEndDate(
        Date.from(
            (LocalDate.of(2018, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    mockInvoice.setInvoiceNote("note");
    mockInvoice.setCreateUser("testCreateUser");
    mockInvoice.setCreateDatetime(new Date());
    mockInvoice.setUpdateUser("testCreateUser");
    mockInvoice.setUpdateDatetime(new Date());
    mockInvoice.setDelFlg("0");

    //モック用Orderデータ作成
    List<Order> mockOrderList = getDefaultOrderList(2);

    //モック作成
    doReturn(mockClient).when(clientRepository).findByClientNo(1000, "0");
    doReturn(mockInvoice).when(invoiceRepository).save(mockInvoice);
    doReturn(new ArrayList<Invoice>()).when(invoiceRepository).findByClientNoAndInvoiceTerm(
        1000,
        Date.from(
            (LocalDate.of(2018, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        Date.from(
            (LocalDate.of(2018, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        "0");
    doReturn(mockOrderList).when(orderRepository).findByClientNoAndInvoiceTerm(
        1000,
        Date.from(
            (LocalDate.of(2018, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        Date.from(
            (LocalDate.of(2018, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())),
        "0");
    doReturn("yyyy-MM-dd").when(apiProperties).getDateFormat();
    doReturn("1.08").when(apiProperties).getConsumptionTax();

    //テスト期待値作成
    InvoiceRegistrationResultResource expectedResult = new InvoiceRegistrationResultResource();
    expectedResult.setInvoiceNo("0");
    expectedResult.setUrl("http://localhost:8080/api/invoices/0");

    //createInvoiceメソッドの引数作成
    InvoiceRegistrationResource testNewInvoice = new InvoiceRegistrationResource();
    testNewInvoice.setClientNo("1000");
    testNewInvoice.setInvoiceTitle("title");
    testNewInvoice.setInvoiceStartDate("2018-01-01");
    testNewInvoice.setInvoiceEndDate("2018-02-01");
    testNewInvoice.setInvoiceNote("note");
    testNewInvoice.setCreateUser("testCreateUser");

    UriComponentsBuilder testUri = UriComponentsBuilder.newInstance();
    testUri.uri(URI.create("http://localhost:8080/"));

    try {

      assertThat(
          service.createInvoice(testNewInvoice, testUri),
          is(samePropertyValuesAs(expectedResult)));

    } catch (Exception e) {
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
    defaultClient.setClientNo(1000);
    defaultClient.setClientChargeLastName("LastName");
    defaultClient.setClientChargeFirstName("FirstName");
    defaultClient.setClientName("ClientName");
    defaultClient.setClientAddress("ClientAddress");
    defaultClient.setClientTel("0000-000-000");
    defaultClient.setClientFax("1111-111-111");

    return defaultClient;

  }

  /**
   * デフォルトのInvoiceデータ.
   *
   * @return the default invoice
   */
  private Invoice getDefaultInvoice() {

    Invoice defaultInvoice = new Invoice();
    defaultInvoice.setInvoiceNo(10000);
    defaultInvoice.setInvoiceStatus("10");

    defaultInvoice.setInvoiceCreateDate(
        Date.from(
            (LocalDate.of(2018, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())));

    defaultInvoice.setInvoiceTitle("testInvoiceTitle");
    defaultInvoice.setInvoiceAmt(100);
    defaultInvoice.setTaxAmt(108);

    defaultInvoice.setInvoiceStartDate(
        Date.from(
            (LocalDate.of(2018, 1, 2).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    defaultInvoice.setInvoiceEndDate(
        Date.from(
            (LocalDate.of(2018, 1, 3).atStartOfDay(ZoneId.systemDefault()).toInstant())));

    defaultInvoice.setInvoiceNote("testInvoiceNote");
    defaultInvoice.setCreateUser("testCreateUser");

    defaultInvoice.setCreateDatetime(
        Date.from(
            (LocalDate.of(2018, 1, 4).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    defaultInvoice.setUpdateUser("testUpdateUser");

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

    for (int i = 0; i < count; i++) {

      Order order = getDefaultOrder();
      order.setItemNo(order.getItemNo() + i);

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
    defaultOrder.setItemNo(500000);
    defaultOrder.setItemName("itemName");
    defaultOrder.setItemType("10");
    defaultOrder.setItemPrice(500);
    defaultOrder.setItemCount(10);
    defaultOrder.setCreateDatetime(
        Date.from(
            (LocalDate.of(2018, 1, 6).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    defaultOrder.setUpdateDatetime(
        Date.from(
            (LocalDate.of(2018, 1, 7).atStartOfDay(ZoneId.systemDefault()).toInstant())));

    return defaultOrder;

  }

}
