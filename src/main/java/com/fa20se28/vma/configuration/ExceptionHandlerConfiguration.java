package com.fa20se28.vma.configuration;

import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.configuration.exception.InvalidFirebaseMessagingException;
import com.fa20se28.vma.configuration.exception.InvalidFirebaseTokenException;
import com.fa20se28.vma.configuration.exception.InvalidParamException;
import com.fa20se28.vma.configuration.exception.RequestAlreadyHandledException;
import com.fa20se28.vma.configuration.exception.ResourceIsInUsedException;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.configuration.exception.TemplateException;
import com.fa20se28.vma.configuration.exception.model.ApiError;
import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.messaging.MessagingErrorCode;
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

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Not Found");
        apiError.setDebugMessage(e.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ResourceIsInUsedException.class)
    protected ResponseEntity<Object> handleResourceIsInUsedException(ResourceIsInUsedException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Resource is in used");
        apiError.setDebugMessage(e.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RequestAlreadyHandledException.class)
    protected ResponseEntity<Object> handleRequestAlreadyHandledException(RequestAlreadyHandledException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Already handled");
        apiError.setDebugMessage(e.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(InvalidParamException.class)
    protected ResponseEntity<Object> handleInvalidParamException(InvalidParamException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Does not support");
        apiError.setDebugMessage(e.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    // Handle RunTimeException. Triggered when the exception is happened at runtime
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRunTimeException(RuntimeException e) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        if (e.getLocalizedMessage().contains("SQLServerException")) {
            apiError.setMessage("Database Error");
            String errorMessage = e.getMessage();
            String formattedMessage = errorMessage.substring(errorMessage.lastIndexOf("SQLServerException"));
            apiError.setDebugMessage(formattedMessage);
        } else {
            apiError.setMessage("An internal server error has occurred");
            apiError.setDebugMessage(e.getLocalizedMessage());
        }
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(DataException.class)
    protected ResponseEntity<Object> handleDataException(DataException e) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage("Data Exception");
        apiError.setDebugMessage(e.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(InvalidFirebaseMessagingException.class)
    protected ResponseEntity<Object> handleInvalidFirebaseMessagingException(InvalidFirebaseMessagingException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setDebugMessage(e.getLocalizedMessage());
        apiError.setMessage(checkFirebaseMessagingExceptionType(e.messagingErrorCode));
        return buildResponseEntity(apiError);
    }

    private String checkFirebaseMessagingExceptionType(MessagingErrorCode messagingErrorCode) {
        String message = "";
        if (messagingErrorCode != null) {
            if (messagingErrorCode.equals(MessagingErrorCode.INTERNAL)) {
                message = "Internal server error";
            } else if (messagingErrorCode.equals(MessagingErrorCode.INVALID_ARGUMENT)) {
                message = "One or more arguments specified in the request were invalid";
            } else if (messagingErrorCode.equals(MessagingErrorCode.QUOTA_EXCEEDED)) {
                message = "Sending limit exceeded for the message target";
            } else if (messagingErrorCode.equals(MessagingErrorCode.SENDER_ID_MISMATCH)) {
                message = "The authenticated sender ID is different from the sender ID for the registration token";
            } else if (messagingErrorCode.equals(MessagingErrorCode.THIRD_PARTY_AUTH_ERROR)) {
                message = "APNs certificate or web push auth key was invalid or missing";
            } else if (messagingErrorCode.equals(MessagingErrorCode.UNAVAILABLE)) {
                message = "Cloud Messaging service is temporarily unavailable";
            } else if (messagingErrorCode.equals(MessagingErrorCode.UNREGISTERED)) {
                message = "App instance was unregistered from FCM. This usually means that the token used is no longer valid and a new one must be used";
            }
        }
        return message;
    }

    @ExceptionHandler(InvalidFirebaseTokenException.class)
    protected ResponseEntity<Object> handleInvalidFirebaseTokenException(InvalidFirebaseTokenException e) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
        apiError.setDebugMessage(e.getLocalizedMessage());
        apiError.setMessage(checkFirebaseAuthExceptionType(e.authErrorCode));
        return buildResponseEntity(apiError);
    }

    private String checkFirebaseAuthExceptionType(AuthErrorCode authErrorCode) {
        String message = "";
        if (authErrorCode != null) {
            if (authErrorCode.equals(AuthErrorCode.CERTIFICATE_FETCH_FAILED)) {
                message = "Failed to retrieve public key";
            } else if (authErrorCode.equals(AuthErrorCode.EXPIRED_ID_TOKEN)) {
                message = "The Token is expired";
            } else if (authErrorCode.equals(AuthErrorCode.INVALID_ID_TOKEN)) {
                message = "The ID token is invalid";
            } else if (authErrorCode.equals(AuthErrorCode.PHONE_NUMBER_ALREADY_EXISTS)) {
                message = "A user already exists with the provided phone number";
            } else if (authErrorCode.equals(AuthErrorCode.UID_ALREADY_EXISTS)) {
                message = "A user already exists with the provided UID";
            } else if (authErrorCode.equals(AuthErrorCode.UNAUTHORIZED_CONTINUE_URL)) {
                message = "The domain of the continue URL is not whitelisted";
            } else if (authErrorCode.equals(AuthErrorCode.USER_NOT_FOUND)) {
                message = "No user record found for the given ID";
            }
        }
        return message;
    }

    // Exception Template.
    @ExceptionHandler(TemplateException.class)
    protected ResponseEntity<Object> handleTemplateException(TemplateException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }
}
