package tigers.cave.webm.invoice.api.resource;

import java.io.Serializable;

import lombok.Data;

/**
 * 請求書一覧取得の請求書一覧部分のレスポンスボディ.
 */
@Data
public class InvoiceResource implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7795405831374721642L;

	/** The invoice no. */
	private String invoiceNo;

	/** The client no. */
	private String clientNo;

	/** The client charge name. */
	private String clientChargeName;

	/** The client name. */
	private String clientName;

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

	/** The url. */
	private String url;

}
