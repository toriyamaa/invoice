package tigers.cave.webm.invoice.api.resource;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import tigers.cave.webm.invoice.api.validation.AlphaNumeric;
import tigers.cave.webm.invoice.api.validation.DateTimeCompare;
import tigers.cave.webm.invoice.api.validation.DateTimeFormatValid;
import tigers.cave.webm.invoice.api.validation.Numeric;

/**
 * 請求書作成のリクエストボデイ
 */
@Data
@DateTimeCompare(message = "InvalidRelation", endDay = "invoiceEndDate", startDay = "invoiceStartDate")
public class InvoiceRegistrationResource implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4499662951708025229L;

	/** The client no. */
	@NotNull(message = "Empty")
	@Numeric(message = "NotNumber")
	private String clientNo;

	/** The invoice title. */
	@Size(max = 300, message = "InvalidWordCount")
	private String invoiceTitle;

	/** The invoice start date. */
	@NotNull(message = "Empty")
	@DateTimeFormatValid(message = "InvalidDateFormat")
	private String invoiceStartDate;

	/** The invoice end date. */
	@NotNull(message = "Empty")
	@DateTimeFormatValid(message = "InvalidDateFormat")
	private String invoiceEndDate;

	/** The invoice note. */
	@Size(max = 3000, message = "InvalidWordCount")
	private String invoiceNote;

	/** The create user. */
	@NotNull(message = "Empty")
	@AlphaNumeric(message = "NotHalfwidthAlphanumeric")
	private String createUser;

}
