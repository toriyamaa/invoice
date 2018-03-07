package tigers.cave.webm.invoice.api.common;

import java.io.Serializable;

import lombok.Getter;

/**
 * アプリケーションエラー時の例外クラスの詳細部分.
 */
@Getter
public class ApplicationExceptionDetail implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -1216655757391423948L;

  /** The code. */
  private String code;

  /** The message option. */
  private String[] messageOption;

  /** The field. */
  private String field;

  /**
   * Instantiates a new detail.
   *
   * @param code the code
   * @param messageOption the message option
   * @param field the field
   */
  public ApplicationExceptionDetail(String code, String[] messageOption, String field) {
    this.code = code;
    this.messageOption = messageOption;
    this.field = field;
  }

}
