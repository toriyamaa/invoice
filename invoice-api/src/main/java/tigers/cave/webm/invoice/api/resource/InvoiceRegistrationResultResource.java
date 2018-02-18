package tigers.cave.webm.invoice.api.resource;

import java.io.Serializable;

import lombok.Data;

/**
 * 請求書作成のレスポンスボデイ
 */
@Data
public class InvoiceRegistrationResultResource implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2476014088249407277L;

	/** The invoice no. */
	private String invoiceNo;

	/** The url. */
	private String url;

}
