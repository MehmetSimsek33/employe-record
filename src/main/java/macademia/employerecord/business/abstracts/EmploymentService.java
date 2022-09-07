package macademia.employerecord.business.abstracts;

import macademia.employerecord.entities.concrete.Employment;
import macademia.employerecord.entities.dto.EmploymentDto;
import macademia.employerecord.entities.response.EmploymentResponse;

public interface EmploymentService {


    EmploymentDto addEmployment(EmploymentDto employmentDto);

    EmploymentDto getEmploymentById(int id);
    Employment checkIfEmploymentExistsById(int id);

    EmploymentDto updateEmployment(EmploymentDto employmentDto, int id);

    void deleteEmploymentById(int id);

    EmploymentResponse getAllEmployment(int pageNo, int pageSize, String sortBy, String sortDir);
}
