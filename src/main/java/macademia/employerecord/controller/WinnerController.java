package macademia.employerecord.controller;

import macademia.employerecord.core.excaption.utilities.AppConstants;
import macademia.employerecord.entities.concrete.Winner;
import macademia.employerecord.entities.dto.DepartmentDto;
import macademia.employerecord.entities.response.DeparmentResponse;
import macademia.employerecord.entities.response.WinnerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import macademia.employerecord.business.abstracts.WinnerService;
import macademia.employerecord.entities.dto.WinnerDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/winner")
public class WinnerController {

	@Autowired
	WinnerService winnerService;

	@PostMapping("/add")
	public ResponseEntity<WinnerDto> addWinner(@RequestBody WinnerDto winnerDto) {
		return new ResponseEntity<>(winnerService.addWinner(winnerDto), HttpStatus.CREATED);

	}
	@PutMapping("/update/{id}")
	public ResponseEntity<WinnerDto> updateDepartment(@Valid @RequestBody WinnerDto winnerDto, @PathVariable(name = "id") int id) {
		WinnerDto winnerResponse = winnerService.updateWinner(winnerDto, id);
		return new ResponseEntity<>(winnerResponse, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable(name = "id") int id) {
		winnerService.deleteWinnerById(id);
		return new ResponseEntity<>("Winner entity deleted success", HttpStatus.OK);
	}
	@GetMapping("/getByWinnerId/{id}")
	public ResponseEntity<WinnerDto> getByWinnerId(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(winnerService.getWinnerById(id));
	}
	@GetMapping("/getAll")
	public WinnerResponse getAllAdress(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

	) {
		return winnerService.getAllWinner(pageNo, pageSize, sortBy, sortDir);
	}
}
