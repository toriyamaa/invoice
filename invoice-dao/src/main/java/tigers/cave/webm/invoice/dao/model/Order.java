package tigers.cave.webm.invoice.dao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the order_tbl database table.
 *
 */
@Entity
@Table(name = "order_tbl")
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
public class Order implements Serializable {

  private static final long serialVersionUID = -3629855706785177461L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_no")
  private int orderNo;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_datetime")
  private Date createDatetime;

  @Column(name = "del_flg")
  private String delFlg;

  @Column(name = "item_count")
  private int itemCount;

  @Column(name = "item_name")
  private String itemName;

  @Column(name = "item_no")
  private int itemNo;

  @Column(name = "item_price")
  private int itemPrice;

  @Column(name = "item_type")
  private String itemType;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "update_datetime")
  private Date updateDatetime;

  //bi-directional many-to-one association to Client
  @ManyToOne
  @JoinColumn(name = "client_no")
  private Client clientTbl;

  public Order() {
  }

  public int getOrderNo() {
    return this.orderNo;
  }

  public void setOrderNo(int orderNo) {
    this.orderNo = orderNo;
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

  public int getItemCount() {
    return this.itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  public String getItemName() {
    return this.itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public int getItemNo() {
    return this.itemNo;
  }

  public void setItemNo(int itemNo) {
    this.itemNo = itemNo;
  }

  public int getItemPrice() {
    return this.itemPrice;
  }

  public void setItemPrice(int itemPrice) {
    this.itemPrice = itemPrice;
  }

  public String getItemType() {
    return this.itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public Date getUpdateDatetime() {
    return this.updateDatetime;
  }

  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = updateDatetime;
  }

  public Client getClientTbl() {
    return this.clientTbl;
  }

  public void setClientTbl(Client clientTbl) {
    this.clientTbl = clientTbl;
  }

}