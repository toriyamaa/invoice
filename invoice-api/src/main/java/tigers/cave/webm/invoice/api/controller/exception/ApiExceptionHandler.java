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

import tigers.cave.webm.invoice.api.resource.err.ApiError;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	MessageSource messageSource;

	private ApiError createApiError(Exception ex, String code, String message) {

		ApiError apiError = new ApiError(code, message);
		return apiError;

	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ApiError apiError = null;
		if (ex instanceof HttpMessageNotReadableException) {

			apiError = createApiError(ex, "E400001", getMessage("E400001", new String[] {}, request));
			status = HttpStatus.BAD_REQUEST;

		} else if (ex instanceof HttpMediaTypeNotSupportedException) {

			apiError = createApiError(ex, "E400002", getMessage("E400002", new String[] {}, request));
			status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

		} else {
			apiError = createApiError(ex, "E500000", getMessage("E500000", new String[] {}, request));
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return super.handleExceptionInternal(ex, apiError, headers, status, request);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ApiError apiError = createApiError(ex, "E400000", getMessage("E400000", new String[] {}, request));

		ex.getBindingResult().getGlobalErrors().stream()
				.forEach(e -> apiError.addDetail(e.getDefaultMessage(), getMessage(e, request), e.getObjectName()));

		ex.getBindingResult().getFieldErrors().stream()
				.forEach(e -> apiError.addDetail(e.getDefaultMessage(), getMessage(e, request), e.getField()));

		return super.handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);

	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ApiError apiError = createApiError(ex, "E400000", getMessage("E400000", new String[] {}, request));

		ex.getBindingResult().getGlobalErrors().stream()
				.forEach(e -> apiError.addDetail(e.getDefaultMessage(), getMessage(e, request), e.getObjectName()));

		ex.getBindingResult().getFieldErrors().stream()
				.forEach(e -> apiError.addDetail(e.getDefaultMessage(), getMessage(e, request), e.getField()));

		return super.handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {

		ApiError apiError = createApiError(ex, "E400000", getMessage("E400000", new String[] {}, request));
		return super.handleExceptionInternal(ex, apiError, null, HttpStatus.BAD_REQUEST, request);

	}

	@ExceptionHandler
	public ResponseEntity<Object> handleSystemException(Exception ex, WebRequest request) {

		ApiError apiError = createApiError(ex, "E500000", getMessage("E500000", new String[] {}, request));
		return super.handleExceptionInternal(ex, apiError, null, HttpStatus.INTERNAL_SERVER_ERROR, request);

	}

	private String getMessage(MessageSourceResolvable resolvable, WebRequest request) {
		return messageSource.getMessage(resolvable, request.getLocale());
	}

	private String getMessage(String code, Object[] args, WebRequest request) {
		return messageSource.getMessage(code, args, request.getLocale());
	}

}
