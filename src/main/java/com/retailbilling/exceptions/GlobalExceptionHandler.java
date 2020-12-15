
package com.retailbilling.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.retailbilling.models.APIError;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
		APIError apiError = new APIError();

		apiError.setStatus(HttpStatus.BAD_REQUEST);

		List<String> errors = new ArrayList<>();
		errors.add(e.getMessage());
		apiError.setErrors(errors);

		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler(value = InvalidRequestException.class)
	public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException e) {
		APIError apiError = new APIError();

		apiError.setStatus(HttpStatus.BAD_REQUEST);

		List<String> errors = new ArrayList<>();
		errors.add(e.getMessage());
		apiError.setErrors(errors);

		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		List<String> errors = fieldErrors.stream().map(err -> err.getField() + ":" + err.getDefaultMessage())
				.collect(Collectors.toList());

		APIError apiError = new APIError();
		apiError.setErrors(errors);
		apiError.setStatus(HttpStatus.BAD_REQUEST);
		apiError.setPath(request.getDescription(false));

		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex,
			ServletWebRequest request) {

		Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
		List<String> errors = constraintViolations.stream()
				.map(err -> err.getRootBeanClass().getName() + " " + err.getPropertyPath() + ": " + err.getMessage())
				.collect(Collectors.toList());

		APIError apiError = new APIError();
		apiError.setErrors(errors);
		apiError.setStatus(HttpStatus.BAD_REQUEST);
		apiError.setPath(request.getRequest().getRequestURI());
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception e) {
		APIError apiError = new APIError();
		apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

		List<String> errors = new ArrayList<>();
		errors.add(e.getMessage());
		apiError.setErrors(errors);

		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());

	}

}
