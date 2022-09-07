package macademia.employerecord.core.excaption;

import org.springframework.http.HttpStatus;

public class EmployeAPIExcaption extends RuntimeException {

	private HttpStatus status;
	private String message;

	public EmployeAPIExcaption(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}