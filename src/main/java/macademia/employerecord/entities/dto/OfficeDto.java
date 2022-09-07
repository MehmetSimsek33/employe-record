package macademia.employerecord.entities.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeDto {
	private int id;
	
	@NotEmpty(message = "Office name should not be null or empty")
	@Size(min=2)
	private String officeName;

	@NotEmpty(message = "Phone  should not be null or empty")
	@Size(min=10,max=16)
	private String officePhone;
	
	private int departmentId;

	

}
