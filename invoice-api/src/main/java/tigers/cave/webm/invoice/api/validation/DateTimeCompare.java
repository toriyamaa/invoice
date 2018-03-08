package tigers.cave.webm.invoice.api.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import tigers.cave.webm.invoice.api.common.validation.DateTimeCompareValidator;

/**
 * The Interface DateTimeCompare.
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE, TYPE })
@Constraint(validatedBy = { DateTimeCompareValidator.class })
@ReportAsSingleViolation
public @interface DateTimeCompare {

  /**
   * Message.
   *
   * @return the string
   */
  String message() default "{tigers.cave.webm.invoice.api.validation.DateTimeCompare.message}";

  /**
   * Groups.
   *
   * @return the class[]
   */
  Class<?>[] groups() default {};

  /**
   * Payload.
   *
   * @return the class<? extends payload>[]
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * Start day.
   *
   * @return the string
   */
  String startDay();

  /**
   * End day.
   *
   * @return the string
   */
  String endDay();

  /**
   * The Interface List.
   */
  @Documented
  @Retention(RUNTIME)
  @Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE, TYPE })
  public @interface List {

    /**
     * Value.
     *
     * @return the date time compare[]
     */
    DateTimeCompare[] value();
  }

}
