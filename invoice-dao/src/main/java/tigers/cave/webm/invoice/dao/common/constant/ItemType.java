package tigers.cave.webm.invoice.dao.common.constant;

/**
 * The Enum ItemType.
 */
public enum ItemType implements EnumEncodable<String> {

	/**  10:書籍. */
	BOOKS("10", "書籍"),

	/**  20:PC. */
	PC("20", "PC"),

	/**  90:その他. */
	OTHER("90", "その他");

	/**  デコーダー. */
	private static final EnumDecoder<String, ItemType> DECODER = EnumDecoder.create(values());

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
	private ItemType(String code, String name) {
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
	public static ItemType decode(String code) {
		return DECODER.decode(code);
	}

}
