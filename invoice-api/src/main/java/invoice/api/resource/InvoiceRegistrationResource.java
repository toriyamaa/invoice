package invoice.api.resource;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRegistrationResource implements Serializable {

	private static final long serialVersionUID = 4499662951708025229L;

	private String clientNo;

	private String invoiceTitle;

	private String invoiceStartDate;

	private String invoiceEndDate;

	private String invoiceNote;

	private String createUser;

}
