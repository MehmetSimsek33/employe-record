package macademia.employerecord.business.abstracts;

import java.util.List;

import macademia.employerecord.entities.concrete.Adress;
import macademia.employerecord.entities.dto.AdressDto;
import macademia.employerecord.entities.response.AdressResponse;

public interface AdressService {
	AdressDto addAdress(AdressDto adressDto);

	AdressDto getAdressById(int id);

	AdressDto updateAdress(AdressDto adressDto, int id);

	void deleteAdressById(int id);

	AdressResponse getAllAdress(int pageNo, int pageSize, String sortBy, String sortDir);

	Adress getByAdress(int id);

}
