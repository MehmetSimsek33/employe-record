package macademia.employerecord.entities.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import macademia.employerecord.entities.concrete.Employe;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WinnerDto {

	private int id;

	private String winner;
	
	private int employeId;
	
	private Date createdAt;
}
