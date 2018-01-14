package invoice.dao.model;

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
 * The persistent class for the invoice_tbl database table.
 *
 */
@Entity
@Table(name="invoice_tbl")
@NamedQuery(name="Invoice.findAll", query="SELECT i FROM Invoice i")
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="invoice_no")
	private int invoiceNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_datetime")
	private Date createDatetime;

	@Column(name="create_user")
	private String createUser;

	@Column(name="del_flg")
	private String delFlg;

	@Column(name="invoice_amt")
	private int invoiceAmt;

	@Temporal(TemporalType.DATE)
	@Column(name="invoice_create_date")
	private Date invoiceCreateDate;

	@Temporal(TemporalType.DATE)
	@Column(name="invoice_end_date")
	private Date invoiceEndDate;

	@Column(name="invoice_note")
	private String invoiceNote;

	@Temporal(TemporalType.DATE)
	@Column(name="invoice_start_date")
	private Date invoiceStartDate;

	@Column(name="invoice_status")
	private String invoiceStatus;

	@Column(name="invoice_title")
	private String invoiceTitle;

	@Column(name="tax_amt")
	private int taxAmt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_datetime")
	private Date updateDatetime;

	@Column(name="update_user")
	private String updateUser;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_no")
	private Client clientTbl;

	public Invoice() {
	}

	public int getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public int getInvoiceAmt() {
		return this.invoiceAmt;
	}

	public void setInvoiceAmt(int invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public Date getInvoiceCreateDate() {
		return this.invoiceCreateDate;
	}

	public void setInvoiceCreateDate(Date invoiceCreateDate) {
		this.invoiceCreateDate = invoiceCreateDate;
	}

	public Date getInvoiceEndDate() {
		return this.invoiceEndDate;
	}

	public void setInvoiceEndDate(Date invoiceEndDate) {
		this.invoiceEndDate = invoiceEndDate;
	}

	public String getInvoiceNote() {
		return this.invoiceNote;
	}

	public void setInvoiceNote(String invoiceNote) {
		this.invoiceNote = invoiceNote;
	}

	public Date getInvoiceStartDate() {
		return this.invoiceStartDate;
	}

	public void setInvoiceStartDate(Date invoiceStartDate) {
		this.invoiceStartDate = invoiceStartDate;
	}

	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getInvoiceTitle() {
		return this.invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public int getTaxAmt() {
		return this.taxAmt;
	}

	public void setTaxAmt(int taxAmt) {
		this.taxAmt = taxAmt;
	}

	public Date getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Client getClientTbl() {
		return this.clientTbl;
	}

	public void setClientTbl(Client clientTbl) {
		this.clientTbl = clientTbl;
	}

}