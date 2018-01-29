package tigers.cave.webm.invoice.api.resource.err;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError implements Serializable {

	private static final long serialVersionUID = -4348582101111315545L;

	private String code;

	private String message;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<ApiDetailError> details = new ArrayList<ApiDetailError>();

	public ApiError(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public void addDetail(String code, String message, String target) {
		this.details.add(new ApiDetailError(code, message, target));
	}

}
