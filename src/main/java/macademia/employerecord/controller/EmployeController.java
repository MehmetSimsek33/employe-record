package macademia.employerecord.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import macademia.employerecord.business.abstracts.EmployeService;
import macademia.employerecord.core.excaption.utilities.AppConstants;
import macademia.employerecord.entities.concrete.Employe;
import macademia.employerecord.entities.dto.EmployeDto;
import macademia.employerecord.entities.response.EmployeResponse;

@RestController
@RequestMapping("/api/employe")
public class EmployeController {

	@Autowired
	private EmployeService employeService; 

	@PostMapping("/add")
	public ResponseEntity<EmployeDto> createPost(@RequestBody EmployeDto employeDto) {
		return new ResponseEntity<>(employeService.addEmploye(employeDto), HttpStatus.CREATED);
	}

	@GetMapping("/getByEmployeId/{id}")
	public ResponseEntity<Employe> getByEmployeId(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(employeService.getByEmployeId(id));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<EmployeDto> updateEmploye(@RequestBody EmployeDto departmentDto,
			@PathVariable(name = "id") int id) {
		EmployeDto employeResponse = employeService.updateEmploye(departmentDto, id);
		return new ResponseEntity<>(employeResponse, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmploye(@PathVariable(name = "id") int id) {
		employeService.deleteEmployeById(id);
		return new ResponseEntity<>("Post entity deleted success", HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public EmployeResponse getAllEmploye(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

	) {
		return employeService.getAllEmploye(pageNo, pageSize, sortBy, sortDir);
	}

	@GetMapping("/getFilterSalary")
	public List<EmployeDto> getFilterSalary(@RequestParam int wage, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return this.employeService.getFilterSalary(wage, date); 
	} 
 
}
