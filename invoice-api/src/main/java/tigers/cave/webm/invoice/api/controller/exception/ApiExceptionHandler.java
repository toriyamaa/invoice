package tigers.cave.webm.invoice.api.controller.exception;

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

		//TODO
		ApiError apiError = null;
		if (ex instanceof BindException) {

			apiError = createApiError(ex, "E400000", "不正なリクエストです。");
			status = HttpStatus.BAD_REQUEST;

		} else if (ex instanceof HttpMessageNotReadableException) {

			apiError = createApiError(ex, "E400001", "JSON形式が不正です。");
			status = HttpStatus.BAD_REQUEST;

		} else if (ex instanceof HttpMediaTypeNotSupportedException) {

			apiError = createApiError(ex, "E400002", "Content-Typeが不正です。");
			status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

		} else {
			apiError = createApiError(ex, "", ex.getMessage());
		}

		return super.handleExceptionInternal(ex, apiError, headers, status, request);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ApiError apiError = createApiError(ex, "E400000", "不正なリクエストです。");

		ex.getBindingResult().getGlobalErrors().stream()
				.forEach(e -> apiError.addDetail(e.getDefaultMessage(), getMessage(e, request), e.getObjectName()));

		ex.getBindingResult().getFieldErrors().stream()
				.forEach(e -> apiError.addDetail(e.getDefaultMessage(), getMessage(e, request), e.getField()));

		return super.handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);

	}

	@ExceptionHandler
	public ResponseEntity<Object> handleSystemException(Exception ex, WebRequest request) {

		//TODO
		ApiError apiError = createApiError(ex, "E500000", "システムエラーです。");
		return super.handleExceptionInternal(ex, apiError, null, HttpStatus.INTERNAL_SERVER_ERROR, request);

	}

	private String getMessage(MessageSourceResolvable resolvable, WebRequest request) {
		return messageSource.getMessage(resolvable, request.getLocale());
	}

}
