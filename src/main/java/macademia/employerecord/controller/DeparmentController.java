package macademia.employerecord.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import macademia.employerecord.business.abstracts.DepartmentService;
import macademia.employerecord.core.excaption.utilities.AppConstants;
import macademia.employerecord.entities.dto.DepartmentDto;
import macademia.employerecord.entities.response.DeparmentResponse;


@RestController
@RequestMapping("/api/department")
public class DeparmentController {

	@Autowired
	private DepartmentService departmentService;

	@PostMapping("/add")
	public ResponseEntity<DepartmentDto> addDepartment(@Valid @RequestBody DepartmentDto departmentDto) {

		return new ResponseEntity<>(departmentService.addDepartment(departmentDto), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<DepartmentDto> updateDepartment(@Valid @RequestBody DepartmentDto departmentDto, @PathVariable(name = "id") int id) {
		DepartmentDto departmentResponse = departmentService.updateDepartment(departmentDto, id);
		return new ResponseEntity<>(departmentResponse, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable(name = "id") int id) {
		departmentService.deleteDepartmenById(id);
		return new ResponseEntity<>("Post entity deleted success", HttpStatus.OK);
	}

	@GetMapping("/getByDepartmentId/{id}")
	public ResponseEntity<DepartmentDto> getByDepartmentId(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(departmentService.getDeparmentById(id));
	}

	@GetMapping("/getAll")
	public DeparmentResponse getAllAdress(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

	) { 
		return departmentService.getAllDepartment(pageNo, pageSize, sortBy, sortDir);
	}

}
