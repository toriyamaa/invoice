package tigers.cave.webm.invoice.api.resource.query;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceListResourceQuery implements Serializable {

	private static final long serialVersionUID = -388688920418678467L;

	private String start;

	private String maxCount;

	private String clientNo;

	private String invoiceStatus;

	private String invoiceDateMin;

	private String invoiceDateMax;


}
