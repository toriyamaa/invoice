package tigers.cave.webm.invoice.dao.repository.criteria;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceCriteria implements Serializable {

	private static final long serialVersionUID = -6820592608925699839L;

	private Integer start;

	private Integer maxCount;

	private Integer clientNo;

	private String invoiceStatus;

	private Date invoiceDateMin;

	private Date invoiceDateMax;

}
