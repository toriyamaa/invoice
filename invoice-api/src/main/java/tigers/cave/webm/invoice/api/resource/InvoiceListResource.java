package tigers.cave.webm.invoice.api.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceListResource implements Serializable {

	private static final long serialVersionUID = 6206777908544280961L;

	private String invoicesMaxCount;

	private String start;

	private String invoicesCount;

	private List<InvoiceResource> invoices;

}
