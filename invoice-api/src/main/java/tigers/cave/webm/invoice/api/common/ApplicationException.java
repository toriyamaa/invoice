package tigers.cave.webm.invoice.api.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Gets the detail list.
 *
 * @return the detail list
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
	private List<Detail> detailList = new ArrayList<Detail>();

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
		detailList.add(new Detail(code, messageOption, field));
	}

	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	@Getter
	public class Detail implements Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 3581845328490261650L;

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
		public Detail(String code, String[] messageOption, String field) {
			this.code = code;
			this.messageOption = messageOption;
			this.field = field;
		}

	}

}
