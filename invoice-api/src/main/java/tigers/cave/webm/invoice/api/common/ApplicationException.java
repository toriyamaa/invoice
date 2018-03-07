package tigers.cave.webm.invoice.api.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * アプリケーションエラー時の例外クラス.
 */
@Getter
public class ApplicationException extends Exception {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 7172489376369635057L;

  /** The code. */
  private String code;

  /** The message option. */
  private String[] messageOption;

  /** The detail list. */
  private List<ApplicationExceptionDetail> detailList = new ArrayList<ApplicationExceptionDetail>();

  /**
   * Instantiates a new application exception.
   *
   * @param code the code
   * @param messageOption the message option
   */
  public ApplicationException(String code, String[] messageOption) {
    super();
    this.code = code;
    this.messageOption = messageOption;
  }

  /**
   * Adds the detail list.
   *
   * @param code the code
   * @param messageOption the message option
   * @param field the field
   */
  public void addDetailList(String code, String[] messageOption, String field) {
    detailList.add(new ApplicationExceptionDetail(code, messageOption, field));
  }

}
