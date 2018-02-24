package tigers.cave.webm.invoice.api.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import tigers.cave.webm.invoice.api.common.validation.DateTimeCompareValidator;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE, TYPE })
@Constraint(validatedBy = { DateTimeCompareValidator.class })
@ReportAsSingleViolation
public @interface DateTimeCompare {

	String message() default "{tigers.cave.webm.invoice.api.validation.DateTimeCompare.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String startDay();

	String endDay();

	@Documented
	@Retention(RUNTIME)
	@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE, TYPE })
	public @interface List {
		DateTimeCompare[] value();
	}

}
