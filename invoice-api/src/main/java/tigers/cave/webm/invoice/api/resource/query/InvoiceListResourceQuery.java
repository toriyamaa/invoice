package tigers.cave.webm.invoice.api.resource.query;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import tigers.cave.webm.invoice.api.validation.DateTimeCompare;
import tigers.cave.webm.invoice.api.validation.DateTimeFormatValid;
import tigers.cave.webm.invoice.api.validation.InvoiceStatusValid;
import tigers.cave.webm.invoice.api.validation.Numeric;

@Getter
@Setter
@DateTimeCompare(message = "InvalidRelation", endDay = "invoiceDateMax", startDay = "invoiceDateMin")
public class InvoiceListResourceQuery implements Serializable {

	private static final long serialVersionUID = -388688920418678467L;

	@Numeric(message = "NotNumber")
	private String start;

	@Numeric(message = "NotNumber")
	private String maxCount;

	@Numeric(message = "NotNumber")
	private String clientNo;

	@InvoiceStatusValid(message = "InvalidInvoiceStatus")
	private String invoiceStatus;

	@DateTimeFormatValid(message = "InvalidDateFormat")
	private String invoiceDateMin;

	@DateTimeFormatValid(message = "InvalidDateFormat")
	private String invoiceDateMax;


}
