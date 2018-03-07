package tigers.cave.webm.invoice.api.common.constant;

/**
 * The Enum MessageDetailType.
 */
public enum MessageDetailType {

  /**  Empty. */
  EMPTY("Empty"),

  /**  InvalidDateFormat. */
  INVALID_DATE_FORMAT("InvalidDateFormat"),

  /**  InvalidInvoiceStatus. */
  INVALID_INVOICE_STATUS("InvalidInvoiceStatus"),

  /**  NotNumber. */
  NOT_NUMBER("NotNumber"),

  /**  InvalidRange. */
  INVALID_RANGE("InvalidRange"),

  /**  InvalidWordCount. */
  INVALID_WORD_COUNT("InvalidWordCount"),

  /**  NotHalfwidthAlphanumeric. */
  NOT_HALFWIDTH_ALPHANUMERIC("NotHalfwidthAlphanumeric"),

  /**  NotExist. */
  NOT_EXIST("NotExist"),

  /**  InvalidRelation. */
  INVALID_RELATION("InvalidRelation"),

  /**  InvoiceDuplication. */
  INVOICE_DUPLICATION("InvoiceDuplication"),

  /**  OrderNotExist. */
  ORDER_NOT_EXIST("OrderNotExist"),

  /**  InvoiceNotExist. */
  INVOICE_NOT_EXIST("InvoiceNotExist");

  /** The detail code. */
  private String detailCode;

  /**
   * Instantiates a new message detail type.
   *
   * @param detailCode the detail code
   */
  private MessageDetailType(String detailCode) {
    this.detailCode = detailCode;
  }

  /**
   * Gets the detail code.
   *
   * @return the detail code
   */
  public String getDetailCode() {
    return this.detailCode;
  }

}
