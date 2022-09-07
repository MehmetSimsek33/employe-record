package macademia.employerecord.business.abstracts;

import macademia.employerecord.entities.concrete.Department;
import macademia.employerecord.entities.concrete.Office;
import macademia.employerecord.entities.dto.DepartmentDto;
import macademia.employerecord.entities.response.DeparmentResponse;

public interface DepartmentService {

	DepartmentDto addDepartment(DepartmentDto departmentDto);

	DepartmentDto getDeparmentById(int id);

	DepartmentDto updateDepartment(DepartmentDto departmentDto, int id);

	void deleteDepartmenById(int id);

	DeparmentResponse getAllDepartment(int pageNo, int pageSize, String sortBy, String sortDir);

	Department getDepartment(int id);

}
