package tigers.cave.webm.invoice.dao.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the client_tbl database table.
 *
 */
@Entity
@Table(name = "client_tbl")
@NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
public class Client implements Serializable {

  private static final long serialVersionUID = -5693621797102247708L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "client_no")
  private int clientNo;

  @Column(name = "client_address")
  private String clientAddress;

  @Column(name = "client_charge_first_name")
  private String clientChargeFirstName;

  @Column(name = "client_charge_last_name")
  private String clientChargeLastName;

  @Column(name = "client_fax")
  private String clientFax;

  @Column(name = "client_name")
  private String clientName;

  @Column(name = "client_tel")
  private String clientTel;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_datetime")
  private Date createDatetime;

  @Column(name = "del_flg")
  private String delFlg;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "update_datetime")
  private Date updateDatetime;

  //bi-directional many-to-one association to Invoice
  @OneToMany(mappedBy = "clientTbl")
  private List<Invoice> invoiceTbls;

  //bi-directional many-to-one association to Order
  @OneToMany(mappedBy = "clientTbl")
  private List<Order> orderTbls;

  public Client() {
  }

  public int getClientNo() {
    return this.clientNo;
  }

  public void setClientNo(int clientNo) {
    this.clientNo = clientNo;
  }

  public String getClientAddress() {
    return this.clientAddress;
  }

  public void setClientAddress(String clientAddress) {
    this.clientAddress = clientAddress;
  }

  public String getClientChargeFirstName() {
    return this.clientChargeFirstName;
  }

  public void setClientChargeFirstName(String clientChargeFirstName) {
    this.clientChargeFirstName = clientChargeFirstName;
  }

  public String getClientChargeLastName() {
    return this.clientChargeLastName;
  }

  public void setClientChargeLastName(String clientChargeLastName) {
    this.clientChargeLastName = clientChargeLastName;
  }

  public String getClientFax() {
    return this.clientFax;
  }

  public void setClientFax(String clientFax) {
    this.clientFax = clientFax;
  }

  public String getClientName() {
    return this.clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public String getClientTel() {
    return this.clientTel;
  }

  public void setClientTel(String clientTel) {
    this.clientTel = clientTel;
  }

  public Date getCreateDatetime() {
    return this.createDatetime;
  }

  public void setCreateDatetime(Date createDatetime) {
    this.createDatetime = createDatetime;
  }

  public String getDelFlg() {
    return this.delFlg;
  }

  public void setDelFlg(String delFlg) {
    this.delFlg = delFlg;
  }

  public Date getUpdateDatetime() {
    return this.updateDatetime;
  }

  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = updateDatetime;
  }

  public List<Invoice> getInvoiceTbls() {
    return this.invoiceTbls;
  }

  public void setInvoiceTbls(List<Invoice> invoiceTbls) {
    this.invoiceTbls = invoiceTbls;
  }

  /**
   * Adds the invoice tbl.
   *
   * @param invoiceTbl the invoice tbl
   * @return the invoice
   */
  public Invoice addInvoiceTbl(Invoice invoiceTbl) {
    getInvoiceTbls().add(invoiceTbl);
    invoiceTbl.setClientTbl(this);

    return invoiceTbl;
  }

  /**
   * Removes the invoice tbl.
   *
   * @param invoiceTbl the invoice tbl
   * @return the invoice
   */
  public Invoice removeInvoiceTbl(Invoice invoiceTbl) {
    getInvoiceTbls().remove(invoiceTbl);
    invoiceTbl.setClientTbl(null);

    return invoiceTbl;
  }

  public List<Order> getOrderTbls() {
    return this.orderTbls;
  }

  public void setOrderTbls(List<Order> orderTbls) {
    this.orderTbls = orderTbls;
  }

  /**
   * Adds the order tbl.
   *
   * @param orderTbl the order tbl
   * @return the order
   */
  public Order addOrderTbl(Order orderTbl) {
    getOrderTbls().add(orderTbl);
    orderTbl.setClientTbl(this);

    return orderTbl;
  }

  /**
   * Removes the order tbl.
   *
   * @param orderTbl the order tbl
   * @return the order
   */
  public Order removeOrderTbl(Order orderTbl) {
    getOrderTbls().remove(orderTbl);
    orderTbl.setClientTbl(null);

    return orderTbl;
  }

}