package tigers.cave.webm.invoice.api.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import tigers.cave.webm.invoice.api.common.validation.DateTimeFormatValidator;

/**
 * The Interface DateTimeFormatValid.
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, ANNOTATION_TYPE })
@Constraint(validatedBy = { DateTimeFormatValidator.class })
@ReportAsSingleViolation
public @interface DateTimeFormatValid {

  /**
   * Message.
   *
   * @return the string
   */
  String message() default "{tigers.cave.webm.invoice.api.validation.DateTimeFormatValid.message}";

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
   * The Interface List.
   */
  @Documented
  @Retention(RUNTIME)
  @Target({ FIELD, ANNOTATION_TYPE })
  public @interface List {

    /**
     * Value.
     *
     * @return the date time format valid[]
     */
    DateTimeFormatValid[] value();
  }

}
