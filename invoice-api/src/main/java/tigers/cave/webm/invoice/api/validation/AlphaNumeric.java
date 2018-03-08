package tigers.cave.webm.invoice.api.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

/**
 * The Interface AlphaNumeric.
 */
@Documented
@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
@ReportAsSingleViolation
@Pattern(regexp = "[a-zA-Z0-9]*")
public @interface AlphaNumeric {

  /**
   * Message.
   *
   * @return the string
   */
  String message() default "{tigers.cave.webm.invoice.api.validation.AlphaNumeric,message}";

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
  @Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
  public @interface List {

    /**
     * Value.
     *
     * @return the alpha numeric[]
     */
    AlphaNumeric[] value();
  }
}
