package tigers.cave.webm.invoice.api.common.validation;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import tigers.cave.webm.invoice.api.validation.DateTimeCompare;

public class DateTimeCompareValidator implements ConstraintValidator<DateTimeCompare, Object> {

	private String message;
	private String startDay;
	private String endDay;

	@Override
	public void initialize(DateTimeCompare constraintAnnotation) {
		this.message = constraintAnnotation.message();
		this.startDay = constraintAnnotation.startDay();
		this.endDay = constraintAnnotation.endDay();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		boolean ret = true;
		if (value != null) {

			BeanWrapper beanWrapper = new BeanWrapperImpl(value);
			String start = (String) beanWrapper.getPropertyValue(startDay);
			String end = (String) beanWrapper.getPropertyValue(endDay);

			if(start == null || end == null) {
				return true;
			}

			Pattern ptn = Pattern.compile("^(\\d{4})[-/]?(\\d{1,2})[-/]?(\\d{1,2})$");

			Matcher mchStart = ptn.matcher(start);
			Matcher mchEnd = ptn.matcher(end);

			LocalDate startDateObj;
			LocalDate endDateObj;

			if (mchStart.find() && mchEnd.find()) {
				try {

					startDateObj = LocalDate.of(Integer.valueOf(mchStart.group(1)), Integer.valueOf(mchStart.group(2)),
							Integer.valueOf(mchStart.group(3)));
					endDateObj = LocalDate.of(Integer.valueOf(mchEnd.group(1)), Integer.valueOf(mchEnd.group(2)),
							Integer.valueOf(mchEnd.group(3)));

				} catch (Exception e) {
					return true;
				}
			} else {
				return true;
			}

			if (endDateObj.compareTo(startDateObj) >= 0) {
				return true;
			} else {

				context.disableDefaultConstraintViolation();

			       context
			           .buildConstraintViolationWithTemplate(message)
			           .addPropertyNode(endDay)
			           .addConstraintViolation();
			       return false;

			}

		}

		return true;

	}

}
