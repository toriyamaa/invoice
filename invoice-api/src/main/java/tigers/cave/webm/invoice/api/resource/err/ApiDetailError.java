package tigers.cave.webm.invoice.api.resource.err;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiDetailError implements Serializable {

	private static final long serialVersionUID = -8081059689008863444L;

	private String code;

	private String message;

	private String target;

	public ApiDetailError(String code, String message, String target) {
		this.code = code;
		this.message = message;
		this.target = target;
	}

}
