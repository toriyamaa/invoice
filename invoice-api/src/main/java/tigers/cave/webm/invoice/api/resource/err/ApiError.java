package tigers.cave.webm.invoice.api.resource.err;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * エラー発生時のレスポンスボディ.
 */
@Data
public class ApiError implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -4348582101111315545L;

  /** The code. */
  private String code;

  /** The message. */
  private String message;

  /** The details. */
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<ApiErrorDetail> details = new ArrayList<ApiErrorDetail>();

  /**
   * Instantiates a new api error.
   *
   * @param code the code
   * @param message the message
   */
  public ApiError(String code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * Adds the detail.
   *
   * @param code the code
   * @param message the message
   * @param target the target
   */
  public void addDetail(String code, String message, String target) {
    this.details.add(new ApiErrorDetail(code, message, target));
  }

}
