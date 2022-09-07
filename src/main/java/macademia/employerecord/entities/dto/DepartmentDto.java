package macademia.employerecord.entities.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import macademia.employerecord.entities.concrete.Employe;
import macademia.employerecord.entities.concrete.Office;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
	
	@JsonIgnore
	private int id;

	@NotEmpty(message = "Department name should not be null or empty")
	@Size(min = 2 , max=45)
	private String departmentName;

	@NotNull
	private int adressId;

	private List<Employe> employes;

	private List<Office> offices;
}
