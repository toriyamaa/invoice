package invoice.api.resource;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRegistrationResultResource implements Serializable {

	private static final long serialVersionUID = 2476014088249407277L;

	private String invoiceNo;

	private String url;

}
