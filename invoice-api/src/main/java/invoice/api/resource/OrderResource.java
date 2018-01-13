package invoice.api.resource;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResource implements Serializable {

	private static final long serialVersionUID = 1857878168009982262L;

	private String itemNo;

	private String itemName;

	private String itemTypeCode;

	private String itemType;

	private String itemPrice;

	private String itemCount;

	private String createDatetime;

	private String updateDatetime;

}
