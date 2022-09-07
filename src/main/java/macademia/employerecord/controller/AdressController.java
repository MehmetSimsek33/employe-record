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

import macademia.employerecord.business.abstracts.AdressService;
import macademia.employerecord.core.excaption.utilities.AppConstants;
import macademia.employerecord.entities.dto.AdressDto;
import macademia.employerecord.entities.response.AdressResponse;

@RestController
@RequestMapping("/api/adress")
public class AdressController {

	@Autowired
	private AdressService adressService;

	@PostMapping("/add")
	public ResponseEntity<AdressDto> createAdress(@Valid @RequestBody AdressDto adresseDto) {
		return new ResponseEntity<>(adressService.addAdress(adresseDto), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<AdressDto> updatePost(@Valid @RequestBody AdressDto adressDto, @PathVariable(name = "id") int id) {
		AdressDto adressResponse = adressService.updateAdress(adressDto, id);
		return new ResponseEntity<>(adressResponse, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") int id) {
		adressService.deleteAdressById(id);
		return new ResponseEntity<>("Post entity deleted success", HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public AdressResponse getAllAdress(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

	) {
		return adressService.getAllAdress(pageNo, pageSize, sortBy, sortDir);
	}

	@GetMapping("/getByAdresId/{id}")
	public ResponseEntity<AdressDto> getaAdressById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(adressService.getAdressById(id));
	}
 
}
