package com.pack.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.pack.exception.UserIdAlreadyExistsException;
import com.pack.exception.UserNotFoundException;
import com.pack.response.ResponseHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {

		//logger.error("User not found");
		return ResponseHandler.generateResponse("User not found", HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage());
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> duplicateEmailException(DataIntegrityViolationException e) {

		//logger.error("EXCEPTION_DUPLICATE_EMAIL");
		return ResponseHandler.generateResponse("EXCEPTION_DUPLICATE_EMAIL", HttpStatus.CONFLICT, null);
	}

	@ExceptionHandler(UserIdAlreadyExistsException.class)
	public ResponseEntity<Object> handleUserIdAlreadyExistsException(UserIdAlreadyExistsException exception) {

		//logger.error("User with given Id already exists");

		return ResponseHandler.generateResponse("User with given Id already exists", HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage());
	}
	
	@ExceptionHandler(CardNotFoundException.class)
	public ResponseEntity<Object> handleCardNotFoundException(CardNotFoundException exception) {

		//logger.error("User not found");
		return ResponseHandler.generateResponse("Card not found", HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage());
	}

	@ExceptionHandler(CardAlreadyExistsException.class)
	public ResponseEntity<Object> handleCardAlreadyExistsException(CardAlreadyExistsException exception) {

		//logger.error("User with given Id already exists");

		return ResponseHandler.generateResponse("Card with given number already exists", HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage());
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException e) {

		//logger.error("EXCEPTION_DUPLICATE_EMAIL");
		return ResponseHandler.generateResponse("Please enter expiry date in a valid format", HttpStatus.CONFLICT, null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)

	protected ResponseEntity<Object> handleInvalidData(MethodArgumentNotValidException ex, WebRequest req) {
		Map<String, Object> resbody = new LinkedHashMap();

		resbody.put("timestamp", new Date());

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(ferror -> ferror.getDefaultMessage())
				.toList();

		resbody.put("Errors ", errors);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resbody);

	}

}