package macademia.employerecord.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class Deneme {
	
	@GetMapping("/get")
	public String mesaj() {
		
		return "deneme";
	}

}
