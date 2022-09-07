package macademia.employerecord.entities.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeDto {

	@JsonIgnore
	private int id;

	@NotEmpty(message = "First name should not be null or empty")
	@Size(min = 2)
	private String firstName;
	@NotEmpty(message = "Last name should not be null or empty")
	@Size(min = 2)
	private String lastName;
	@NotEmpty(message = "Last name should not be null or empty")
	@Email
	private String email;

	private LocalDate startDate;

	private int wage;

	private int departmentId;

	private  int employmentId;


}
