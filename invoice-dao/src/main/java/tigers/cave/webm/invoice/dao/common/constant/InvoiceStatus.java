package tigers.cave.webm.invoice.dao.common.constant;

/**
 * The Enum InvoiceStatus.
 */
public enum InvoiceStatus implements EnumEncodable<String> {

  /**  10:新規作成. */
  NEW("10", "新規作成"),

  /**  20:送付済. */
  FINISH_SEND("20", "送付済"),

  /**  30:入金確認済. */
  FINISH_CONFIRM_PAYMENT("30", "入金確認済"),

  /**  90:廃棄. */
  ABOLISHMENT("90", "廃棄");

  /**  デコーダー. */
  private static final EnumDecoder<String, InvoiceStatus> DECODER = EnumDecoder.create(values());

  /**  コード値. */
  private final String code;

  /**  名称. */
  private final String name;

  /**
   * コンストラクタ.
   *
   * @param code コード値
   * @param name 名称
   */
  private InvoiceStatus(String code, String name) {
    this.code = code;
    this.name = name;
  }

  /* (非 Javadoc)
   * @see tigers.cave.webm.invoice.dao.common.constant.EnumEncodable#getCode()
   */
  @Override
  public String getCode() {
    return this.code;
  }

  /* (非 Javadoc)
   * @see tigers.cave.webm.invoice.dao.common.constant.EnumEncodable#getName()
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * コード値からEnumクラスを取得する.
   *
   * @param code コード値
   * @return 受領形式Enumクラス
   */
  public static InvoiceStatus decode(String code) {
    return DECODER.decode(code);
  }

}
