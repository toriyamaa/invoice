package tigers.cave.webm.invoice.api.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

@Documented
@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
@ReportAsSingleViolation
@Pattern(regexp = "[0-9]*")
public @interface Numeric {

	String message() default "{tigers.cave.webm.invoice.api.validation.Numeric,message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Documented
	@Retention(RUNTIME)
	@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
	public @interface List {
		Numeric[] value();
	}
}
