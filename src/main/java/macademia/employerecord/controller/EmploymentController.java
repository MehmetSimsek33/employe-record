package macademia.employerecord.controller;

import macademia.employerecord.business.abstracts.EmployeService;
import macademia.employerecord.business.abstracts.EmploymentService;
import macademia.employerecord.core.excaption.utilities.AppConstants;
import macademia.employerecord.entities.dto.AdressDto;
import macademia.employerecord.entities.dto.EmployeDto;
import macademia.employerecord.entities.dto.EmploymentDto;
import macademia.employerecord.entities.response.AdressResponse;
import macademia.employerecord.entities.response.EmploymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/employment")
public class EmploymentController {
    @Autowired
    private EmploymentService employmentService;

    @PostMapping("/add")
    public ResponseEntity<EmploymentDto> createAdress(@Valid @RequestBody EmploymentDto employmentDto) {
        return new ResponseEntity<>(employmentService.addEmployment(employmentDto), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<EmploymentDto> updatePost(@Valid @RequestBody EmploymentDto employmentDto, @PathVariable(name = "id") int id) {
        EmploymentDto employmentResponse = employmentService.updateEmployment(employmentDto, id);
        return new ResponseEntity<>(employmentResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") int id) {
        employmentService.deleteEmploymentById(id);
        return new ResponseEntity<>("Employment entity deleted success", HttpStatus.OK);
    }
    @GetMapping("/getByEmploymentId/{id}")
    public ResponseEntity<EmploymentDto> getaAdressById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(employmentService.getEmploymentById(id));
    }
    @GetMapping("/getAll")
    public EmploymentResponse getAllAdress(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

    ) {
        return employmentService.getAllEmployment(pageNo, pageSize, sortBy, sortDir);
    }

}
