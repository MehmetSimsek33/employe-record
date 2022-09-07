package macademia.employerecord.business.concrete;

import java.util.List;
import java.util.stream.Collectors;

import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import macademia.employerecord.business.abstracts.AdressService;
import macademia.employerecord.core.excaption.ResourceNotFoundExcaption;
import macademia.employerecord.dataAccess.abstracts.AdressDao;
import macademia.employerecord.entities.concrete.Adress;
import macademia.employerecord.entities.dto.AdressDto;
import macademia.employerecord.entities.response.AdressResponse;

@Service
public class AdressManager implements AdressService {


	private AdressDao adressDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public AdressManager(AdressDao adressDao, ModelMapperService modelMapperService) {
		this.adressDao = adressDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public AdressResponse getAllAdress(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Adress> adress = adressDao.findAll(pageable);

		List<Adress> listOfAdress = adress.getContent();

		List<AdressDto> content = listOfAdress.stream().map(post ->this.modelMapperService.forResponse().map(post, AdressDto.class)).collect(Collectors.toList());
		
	
		AdressResponse adressResponse = new AdressResponse();
		adressResponse.setContent(content);
		adressResponse.setPageNo(adress.getNumber());
		adressResponse.setPageSize(adress.getSize());
		adressResponse.setTotalElemenets(adress.getTotalElements());
		adressResponse.setTotalPages(adress.getTotalPages());
		adressResponse.setLast(adress.isLast());
		return adressResponse;
	}

	@Override
	public AdressDto addAdress(AdressDto adressDto) {

		Adress adress = this.modelMapperService.forRequest().map(adressDto, Adress.class);
		this.adressDao.save(adress);
		return adressDto;
	}

	@Override
	public AdressDto updateAdress(AdressDto adresDto, int id) {

		checkIfAdressExistsById(id);
		Adress updateAdres = this.modelMapperService.forRequest().map(adresDto, Adress.class);
		updateAdres.setId(id);
		adressDao.save(updateAdres);
		return this.modelMapperService.forRequest().map(updateAdres, AdressDto.class);

	}

	@Override
	public void deleteAdressById(int id) {
		Adress adress=checkIfAdressExistsById(id);
		adressDao.delete(adress);
	}

	@Override
	public AdressDto getAdressById(int id) {
		Adress adress = checkIfAdressExistsById(id);
		return this.modelMapperService.forResponse().map(adress, AdressDto.class);
	}

	@Override
	public Adress getByAdress(int id) {
		return  checkIfAdressExistsById(id);
	}

	private Adress checkIfAdressExistsById(int id) {
		Adress adress = adressDao.findById(id).orElseThrow(() -> new ResourceNotFoundExcaption("Adress", "id :", id));
		return adress;
	}


}
