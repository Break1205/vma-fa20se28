package com.fa20se28.vma.configuration;

import com.fa20se28.vma.configuration.exception.InvalidFirebaseTokenException;
import com.fa20se28.vma.configuration.exception.TemplateException;
import com.fa20se28.vma.configuration.exception.model.ApiError;
import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerConfiguration extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    // Handle MissingServletRequestParameterException. Trigger when a 'required' parameter is missing
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    // Handle HttpMediaTypeNotSupportedException. Trigger when JSON is invalid
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
    }

    // Handle ConstraintViolationException. Trigger when @Validated failed
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(apiError);
    }

    // Handle MethodArgumentNotValidException. Triggered when @Valid failed.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    // Handle other exceptions
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleException(IllegalArgumentException e) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage("An error has occurred");
        apiError.setDebugMessage(e.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    // Handle RunTimeException. Triggered when the exception is happened at runtime
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleRunTimeException(RuntimeException e) {
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An internal server error occurred",
                e);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(InvalidFirebaseTokenException.class)
    public ResponseEntity<Object> handleInvalidFirebaseTokenException(InvalidFirebaseTokenException e) {

        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
        apiError.setDebugMessage(e.getLocalizedMessage());
        if (e.authErrorCode.equals(AuthErrorCode.CERTIFICATE_FETCH_FAILED)) {
            apiError.setMessage("Failed to retrieve public key");
        }
        if (e.authErrorCode.equals(AuthErrorCode.EXPIRED_ID_TOKEN)) {
            apiError.setMessage("The Token is expired");
        }
        if (e.authErrorCode.equals(AuthErrorCode.INVALID_ID_TOKEN)) {
            apiError.setMessage("The ID token is invalid");
        }
        if (e.authErrorCode.equals(AuthErrorCode.PHONE_NUMBER_ALREADY_EXISTS)) {
            apiError.setMessage("A user already exists with the provided phone number");
        }
        if (e.authErrorCode.equals(AuthErrorCode.UID_ALREADY_EXISTS)) {
            apiError.setMessage("A user already exists with the provided UID");
        }
        if (e.authErrorCode.equals(AuthErrorCode.INVALID_ID_TOKEN)) {
            apiError.setMessage("The ID token is invalid");
        }
        if (e.authErrorCode.equals(AuthErrorCode.UNAUTHORIZED_CONTINUE_URL)) {
            apiError.setMessage("The domain of the continue URL is not whitelisted");
        }
        if (e.authErrorCode.equals(AuthErrorCode.USER_NOT_FOUND)) {
            apiError.setMessage("No user record found for the given ID");
        }
        return buildResponseEntity(apiError);
    }

    // Exception Template.
    @ExceptionHandler(TemplateException.class)
    protected ResponseEntity<Object> handleTemplateException(TemplateException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }
}
