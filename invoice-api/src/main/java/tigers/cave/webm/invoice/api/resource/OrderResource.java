package tigers.cave.webm.invoice.api.resource;

import java.io.Serializable;

import lombok.Data;

/**
 * 請求書取得の注文実績一覧部分のレスポンスボディ.
 */
@Data
public class OrderResource implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1857878168009982262L;

	/** The item no. */
	private String itemNo;

	/** The item name. */
	private String itemName;

	/** The item type code. */
	private String itemTypeCode;

	/** The item type. */
	private String itemType;

	/** The item price. */
	private String itemPrice;

	/** The item count. */
	private String itemCount;

	/** The create datetime. */
	private String createDatetime;

	/** The update datetime. */
	private String updateDatetime;

}
