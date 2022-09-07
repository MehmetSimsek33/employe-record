package macademia.employerecord.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import macademia.employerecord.entities.concrete.Employe;
import macademia.employerecord.entities.dto.EmployeDto;
import macademia.employerecord.entities.response.EmployeResponse;

public interface EmployeService {

	EmployeDto addEmploye(EmployeDto employeDto);

	EmployeDto getEmployeById(int id);

	EmployeDto updateEmploye(EmployeDto employeDto, int id);

	void deleteEmployeById(int id);



	Employe getByEmployeId(int id);

	EmployeResponse getAllEmploye(int pageNo, int pageSize, String sortBy, String sortDir);
	
	List<EmployeDto> getFilterSalary(int wage, LocalDate date);
	Employe getWinnerSize();

}
