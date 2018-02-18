package tigers.cave.webm.invoice.api.common.constant;

/**
 * The Enum MessageType.
 */
public enum MessageType {

	/**  E400000. */
	BAD_REQUEST("E400000"),

	/**  E400001. */
	BAD_JSON_FORMAT("E400001"),

	/**  E400002. */
	BAD_CONTENT_TYPE("E400002"),

	/**  E500000. */
	SYSTEM_ERROR("E500000");

	/** The code. */
	private String code;

	/**
	 * Instantiates a new message type.
	 *
	 * @param code the code
	 */
	private MessageType(String code) {
		this.code = code;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}
}
