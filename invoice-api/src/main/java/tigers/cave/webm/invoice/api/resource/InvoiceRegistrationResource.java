package tigers.cave.webm.invoice.api.resource;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRegistrationResource implements Serializable {

	private static final long serialVersionUID = 4499662951708025229L;

	@NotNull(message = "Empty")
	private String clientNo;

	@Size(max = 300, message = "InvalidWordCount")
	private String invoiceTitle;

	@NotNull(message = "Empty")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String invoiceStartDate;

	@NotNull(message = "Empty")
	// TODO @DateTimeFormat(pattern = "yyyy-MM-dd")
	private String invoiceEndDate;

	@Size(max = 3000, message = "InvalidWordCount")
	private String invoiceNote;

	@NotNull(message = "Empty")
	private String createUser;

}
