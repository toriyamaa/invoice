package tigers.cave.webm.invoice.api.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 請求書一覧取得のレスポンスボデイ.
 */
@Data
public class InvoiceListResource implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6206777908544280961L;

	/** The invoices max count. */
	private String invoicesMaxCount;

	/** The start. */
	private String start;

	/** The invoices count. */
	private String invoicesCount;

	/** The invoices. */
	private List<InvoiceResource> invoices;

}
