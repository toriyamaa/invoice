package tigers.cave.webm.invoice.api.resource;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import tigers.cave.webm.invoice.api.validation.AlphaNumeric;
import tigers.cave.webm.invoice.api.validation.DateTimeCompare;
import tigers.cave.webm.invoice.api.validation.DateTimeFormatValid;
import tigers.cave.webm.invoice.api.validation.Numeric;

@Getter
@Setter
@DateTimeCompare(message = "InvalidRelation", endDay = "invoiceEndDate", startDay = "invoiceStartDate")
public class InvoiceRegistrationResource implements Serializable {

	private static final long serialVersionUID = 4499662951708025229L;

	@NotNull(message = "Empty")
	@Numeric(message = "NotNumber")
	private String clientNo;

	@Size(max = 300, message = "InvalidWordCount")
	private String invoiceTitle;

	@NotNull(message = "Empty")
	@DateTimeFormatValid(message = "InvalidDateFormat")
	private String invoiceStartDate;

	@NotNull(message = "Empty")
	@DateTimeFormatValid(message = "InvalidDateFormat")
	private String invoiceEndDate;

	@Size(max = 3000, message = "InvalidWordCount")
	private String invoiceNote;

	@NotNull(message = "Empty")
	@AlphaNumeric(message = "NotHalfwidthAlphanumeric")
	private String createUser;

}
