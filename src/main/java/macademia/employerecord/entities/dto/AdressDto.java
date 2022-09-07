package macademia.employerecord.entities.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import macademia.employerecord.entities.concrete.Department;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdressDto {

	@JsonIgnore
	private int id;

	@NotEmpty(message = "Adress should not be null or empty")
	private String adress;

	private List<Department> departments;

}
