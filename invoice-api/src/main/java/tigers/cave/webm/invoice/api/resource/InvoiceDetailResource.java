package tigers.cave.webm.invoice.api.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 請求書取得の請求書部分のレスポンスボデイ.
 */
@Data
public class InvoiceDetailResource implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2201705105441530211L;

	/** The invoice no. */
	private String invoiceNo;

	/** The client no. */
	private String clientNo;

	/** The client charge name. */
	private String clientChargeName;

	/** The client name. */
	private String clientName;

	/** The client address. */
	private String clientAddress;

	/** The client tel. */
	private String clientTel;

	/** The client fax. */
	private String clientFax;

	/** The invoice status code. */
	private String invoiceStatusCode;

	/** The invoice status. */
	private String invoiceStatus;

	/** The invoice create date. */
	private String invoiceCreateDate;

	/** The invoice title. */
	private String invoiceTitle;

	/** The invoice amt. */
	private String invoiceAmt;

	/** The tax amt. */
	private String taxAmt;

	/** The invoice start date. */
	private String invoiceStartDate;

	/** The invoice end date. */
	private String invoiceEndDate;

	/** The invoice note. */
	private String invoiceNote;

	/** The create user. */
	private String createUser;

	/** The create datetime. */
	private String createDatetime;

	/** The update user. */
	private String updateUser;

	/** The update datetime. */
	private String updateDatetime;

	/** The orders count. */
	private String ordersCount;

	/** The orders. */
	private List<OrderResource> orders;

}
