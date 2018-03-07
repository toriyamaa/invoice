package tigers.cave.webm.invoice.api.controller.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import tigers.cave.webm.invoice.api.common.ApplicationException;
import tigers.cave.webm.invoice.api.common.constant.MessageType;
import tigers.cave.webm.invoice.api.resource.err.ApiError;

/**
 * The Class ApiExceptionHandler.
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  /** The message source. */
  @Autowired
  MessageSource messageSource;

  /* (非 Javadoc)
   * @see
   * org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
   * #handleExceptionInternal(
   *   java.lang.Exception,
   *   java.lang.Object,
   *   org.springframework.http.HttpHeaders,
   *   org.springframework.http.HttpStatus,
   *   org.springframework.web.context.request.WebRequest)
   *
   * 例外クラスが以下の場合、statusの設定とレスポンスボディの設定を行う
   *
   * ・HttpMessageNotReadableException
   * ・HttpMediaTypeNotSupportedException
   *
   * ※上記以外はシステムエラーとして扱う
   *
   */
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

    ApiError apiError = null;
    if (ex instanceof HttpMessageNotReadableException) {

      apiError = new ApiError(
          MessageType.BAD_JSON_FORMAT.getCode(),
          getMessage(MessageType.BAD_JSON_FORMAT.getCode(), new String[] {}, request));

      status = HttpStatus.BAD_REQUEST;

    } else if (ex instanceof HttpMediaTypeNotSupportedException) {

      apiError = new ApiError(
          MessageType.BAD_CONTENT_TYPE.getCode(),
          getMessage(MessageType.BAD_CONTENT_TYPE.getCode(), new String[] {}, request));

      status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

    } else {
      apiError = new ApiError(
          MessageType.SYSTEM_ERROR.getCode(),
          getMessage(MessageType.SYSTEM_ERROR.getCode(), new String[] {}, request));

      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    return super.handleExceptionInternal(ex, apiError, headers, status, request);

  }

  /* (非 Javadoc)
   * @see
   * org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
   * #handleMethodArgumentNotValid(
   *   org.springframework.web.bind.MethodArgumentNotValidException,
   *   org.springframework.http.HttpHeaders,
   *   org.springframework.http.HttpStatus,
   *   org.springframework.web.context.request.WebRequest)
   *
   * 例外クラスがMethodArgumentNotValidExceptionの場合、statusの設定とレスポンスボディの設定を行う
   *
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    ApiError apiError = new ApiError(
        MessageType.BAD_REQUEST.getCode(),
        getMessage(MessageType.BAD_REQUEST.getCode(), new String[] {}, request));

    ex.getBindingResult().getGlobalErrors().stream()
        .forEach(e -> apiError.addDetail(e.getDefaultMessage(), getMessage(e, request),
            e.getObjectName()));

    ex.getBindingResult().getFieldErrors().stream()
        .forEach(
            e -> apiError.addDetail(e.getDefaultMessage(), getMessage(e, request), e.getField()));

    return super.handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);

  }

  /* (非 Javadoc)
   * @see
   * org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
   * #handleBindException(
   *   org.springframework.validation.BindException,
   *   org.springframework.http.HttpHeaders,
   *   org.springframework.http.HttpStatus,
   *   org.springframework.web.context.request.WebRequest)
   *
   * 例外クラスがBindExceptionの場合、statusの設定とレスポンスボディの設定を行う
   *
   */
  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    ApiError apiError = new ApiError(
        MessageType.BAD_REQUEST.getCode(),
        getMessage(MessageType.BAD_REQUEST.getCode(), new String[] {}, request));

    ex.getBindingResult().getGlobalErrors().stream()
        .forEach(e -> apiError.addDetail(e.getDefaultMessage(), getMessage(e, request),
            e.getObjectName()));

    ex.getBindingResult().getFieldErrors().stream()
        .forEach(
            e -> apiError.addDetail(e.getDefaultMessage(), getMessage(e, request), e.getField()));

    return super.handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
  }

  /**
   * Handle constraint violation exception.
   * 例外クラスがConstraintViolationExceptionの場合、statusの設定とレスポンスボディの設定を行う
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler
  public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
      WebRequest request) {

    ApiError apiError = new ApiError(
        MessageType.BAD_REQUEST.getCode(),
        getMessage(MessageType.BAD_REQUEST.getCode(), new String[] {}, request));

    return super.handleExceptionInternal(ex, apiError, null, HttpStatus.BAD_REQUEST, request);

  }

  /**
   * Handle application exception
   * 例外クラスがApplicationExceptionの場合、statusの設定とレスポンスボディの設定を行う.
   *
   * @param ae the ae
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler
  public ResponseEntity<Object> handleApplicationException(ApplicationException ae,
      WebRequest request) {

    ApiError apiError = new ApiError(
        ae.getCode(),
        getMessage(ae.getCode(), ae.getMessageOption(), request));

    ae.getDetailList().stream()
        .forEach(
            d -> apiError.addDetail(
                d.getCode(),
                getMessage(d.getCode(), d.getMessageOption(), request),
                d.getField()));

    return super.handleExceptionInternal(ae, apiError, null, HttpStatus.BAD_REQUEST, request);

  }

  /**
   * Handle system exception.
   * 例外クラスがExceptionの場合、statusの設定とレスポンスボディの設定を行う
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler
  public ResponseEntity<Object> handleSystemException(Exception ex, WebRequest request) {

    ApiError apiError = new ApiError(
        MessageType.SYSTEM_ERROR.getCode(),
        getMessage(MessageType.SYSTEM_ERROR.getCode(), new String[] {}, request));

    return super.handleExceptionInternal(ex, apiError, null, HttpStatus.INTERNAL_SERVER_ERROR,
        request);

  }

  /**
   * Gets the message.
   *
   * @param resolvable the resolvable
   * @param request the request
   * @return the message
   */
  private String getMessage(MessageSourceResolvable resolvable, WebRequest request) {
    return messageSource.getMessage(resolvable, request.getLocale());
  }

  /**
   * Gets the message.
   *
   * @param code the code
   * @param args the args
   * @param request the request
   * @return the message
   */
  private String getMessage(String code, Object[] args, WebRequest request) {
    return messageSource.getMessage(code, args, request.getLocale());
  }

}
