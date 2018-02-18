package tigers.cave.webm.invoice.api.common.validation;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import tigers.cave.webm.invoice.api.validation.DateTimeFormatValid;

/**
 * The Class DateTimeFormatValidator.
 *
 * 日付のフォーマットチェックのバリデータ
 * yyyy-MM-dd形式かどうかをチェックする。
 */
public class DateTimeFormatValidator implements ConstraintValidator<DateTimeFormatValid, String> {

	/* (非 Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(DateTimeFormatValid constraintAnnotation) {
	}

	/* (非 Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

	  //必須かどうかはここではチェックしないのでnullは許容
		if (value == null) {
			return true;
		}

		Pattern ptn = Pattern.compile("^(\\d{4})-(\\d{1,2})-(\\d{1,2})$");
		Matcher mch = ptn.matcher(value);

		//フォーマットのチェック
		if (mch.find()) {
			try {
			  //日付の妥当性のチェック
				LocalDate.of(
						Integer.valueOf(mch.group(1)),
						Integer.valueOf(mch.group(2)),
						Integer.valueOf(mch.group(3)));
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

}
