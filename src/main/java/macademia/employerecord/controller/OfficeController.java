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

import macademia.employerecord.business.abstracts.OfficeService;
import macademia.employerecord.core.excaption.utilities.AppConstants;
import macademia.employerecord.entities.dto.OfficeDto;
import macademia.employerecord.entities.response.OfficeResponse;

@RestController
@RequestMapping("/api/office")
public class OfficeController {

	@Autowired
	private OfficeService officeService;

	@PostMapping("/add")
	public ResponseEntity<OfficeDto> createAdress(@Valid @RequestBody OfficeDto officeDto) {

		return new ResponseEntity<>(officeService.addOffice(officeDto), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<OfficeDto> updatePost(@Valid @RequestBody OfficeDto officeDto, @PathVariable(name = "id") int id) {
		OfficeDto officeResponse = officeService.updateOffice(officeDto, id);
		return new ResponseEntity<>(officeResponse, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") int id) {
		officeService.deleteOfficeById(id);
		return new ResponseEntity<>("Post entity deleted success", HttpStatus.OK);
	}

	@GetMapping("/getByDepartmentId/{id}")
	public ResponseEntity<OfficeDto> getaAdressById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(officeService.getOfficeById(id));
	}

	@GetMapping("/getAll")
	public OfficeResponse getAllAdress(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

	) {
		return officeService.getAllOffice(pageNo, pageSize, sortBy, sortDir);
	}

//	@GetMapping("/getByOfficeId/{id}")
//	public ResponseEntity<Office> getByEmployeId(@PathVariable(name = "id") int id) {
//		return ResponseEntity.ok(officeService.getByAdressId(id));
//	}

}
