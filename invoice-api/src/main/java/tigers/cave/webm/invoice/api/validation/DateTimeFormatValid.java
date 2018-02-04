package tigers.cave.webm.invoice.api.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import tigers.cave.webm.invoice.api.common.validation.DateTimeFormatValidator;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, ANNOTATION_TYPE })
@Constraint(validatedBy = {DateTimeFormatValidator.class})
@ReportAsSingleViolation
public @interface DateTimeFormatValid {

	String message() default "{tigers.cave.webm.invoice.api.validation.DateTimeFormatValid.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	@Documented
	@Retention(RUNTIME)
	@Target({ FIELD, ANNOTATION_TYPE})
	public @interface List {
		DateTimeFormatValid[] value();
	}

}
