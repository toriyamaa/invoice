package tigers.cave.webm.invoice.api.resource.err;

import java.io.Serializable;

import lombok.Data;

/**
 * エラー発生時のエラー詳細部分のレスポンスボディ.
 */
@Data
public class ApiErrorDetail implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -8081059689008863444L;

  /** The code. */
  private String code;

  /** The message. */
  private String message;

  /** The target. */
  private String target;

  /**
   * Instantiates a new api error detail.
   *
   * @param code the code
   * @param message the message
   * @param target the target
   */
  public ApiErrorDetail(String code, String message, String target) {
    this.code = code;
    this.message = message;
    this.target = target;
  }

}
