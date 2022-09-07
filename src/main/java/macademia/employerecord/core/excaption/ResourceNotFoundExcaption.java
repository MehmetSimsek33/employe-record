package macademia.employerecord.core.excaption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundExcaption extends RuntimeException {
	private String resourceName;
	private String fieldName;
	private long fieldVaue;

	public ResourceNotFoundExcaption(String resourceName, String fieldName, long fieldVaue) {
		super(String.format("%s not found with %s : %s ", resourceName, fieldName, fieldVaue));// Ex Post not found with
																								// id: 1
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldVaue = fieldVaue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public long getFieldValue() {
		return fieldVaue;
	}
}