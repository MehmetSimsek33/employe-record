package macademia.employerecord.core.excaption;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExcaptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundExcaption.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundExcaption(ResourceNotFoundExcaption excaption,
			WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), excaption.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(EmployeAPIExcaption.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIExcaption(EmployeAPIExcaption excaption, WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), excaption.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalExcaption(Exception excaption, WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), excaption.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

//	@ExceptionHandler(ResourceNotFoundExcaption.class)
//	public ResponseEntity<Object> handleResourceNotFoundExcaption(MethodArgumentNotValidException excaption,
//			WebRequest webRequest) {
//		Map<String, String> errors = new HashMap<>();
//		excaption.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((FieldError) error).getField();
//			String message = error.getDefaultMessage();
//			errors.put(fieldName, message);
//		});
//
//		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
//
//	} 
}