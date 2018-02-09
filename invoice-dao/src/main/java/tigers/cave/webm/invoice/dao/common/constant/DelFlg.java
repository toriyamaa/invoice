package tigers.cave.webm.invoice.dao.common.constant;

/**
 * The Enum DelFlg.
 */
public enum DelFlg {

	/** The not delete. */
	NOT_DELETE("0"),

	/** The delete. */
	DELETE("1");

	/** The value. */
	private String value;

	/**
	 * Instantiates a new del flg.
	 *
	 * @param value the value
	 */
	private DelFlg(String value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}
}
