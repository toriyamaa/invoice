package tigers.cave.webm.invoice.api.resource;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceResource implements Serializable {

	private static final long serialVersionUID = 7795405831374721642L;

	private String invoiceNo;

	private String clientNo;

	private String clientChargeName;

	private String clientName;

	private String invoiceStatusCode;

	private String invoiceStatus;

	private String invoiceCreateDate;

	private String invoiceTitle;

	private String invoiceAmt;

	private String taxAmt;

	private String invoiceStartDate;

	private String invoiceEndDate;

	private String invoiceNote;

	private String createUser;

	private String createDatetime;

	private String updateUser;

	private String updateDatetime;

	private String url;

}
