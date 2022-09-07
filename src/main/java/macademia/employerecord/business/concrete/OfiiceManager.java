package macademia.employerecord.business.concrete;

import java.util.List;
import java.util.stream.Collectors;

import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperService;
import macademia.employerecord.entities.concrete.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import macademia.employerecord.business.abstracts.DepartmentService;
import macademia.employerecord.business.abstracts.OfficeService;
import macademia.employerecord.core.excaption.ResourceNotFoundExcaption;
import macademia.employerecord.dataAccess.abstracts.OfficeDao;
import macademia.employerecord.entities.concrete.Office;
import macademia.employerecord.entities.dto.OfficeDto;
import macademia.employerecord.entities.response.OfficeResponse;

@Service
public class OfiiceManager implements OfficeService {

	private ModelMapperService modelMapperService;
	private OfficeDao officeDao;
	private DepartmentService departmentService;

	@Autowired
	public OfiiceManager(ModelMapperService modelMapperService, OfficeDao officeDao, DepartmentService departmentService) {
		this.modelMapperService = modelMapperService;
		this.officeDao = officeDao;
		this.departmentService = departmentService;
	}

	@Override
	public OfficeDto addOffice(OfficeDto officeDto) {
		Office office= this.modelMapperService.forRequest().map(officeDto, Office.class);
		this.officeDao.save(office);
		return officeDto;
	}

	@Override
	public OfficeDto getOfficeById(int id) {

		Office office = checkIfOfficeExistsById(id);
		return this.modelMapperService.forResponse().map(office, OfficeDto.class);

	}

	@Override
	public OfficeDto updateOffice(OfficeDto officeDto, int id) {
		checkIfOfficeExistsById(id);
		checkIfDepartmentExistsById(officeDto.getDepartmentId());
		Office office  = this.modelMapperService.forRequest().map(officeDto, Office.class);
		office.setId(id);
		this.officeDao.save(office);
		return officeDto;

	}

	@Override
	public void deleteOfficeById(int id) {
		Office office = checkIfOfficeExistsById(id);
		officeDao.delete(office);

	}

	@Override
	public OfficeResponse getAllOffice(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Office> office = officeDao.findAll(pageable);

		List<Office> listOfAdress = office.getContent();
		List<OfficeDto> content = listOfAdress.stream().map(post ->this.modelMapperService.forResponse().map(post, OfficeDto.class)).collect(Collectors.toList());

		OfficeResponse officeResponse = new OfficeResponse();
		officeResponse.setContent(content);
		officeResponse.setPageNo(office.getNumber());
		officeResponse.setPageSize(office.getSize());
		officeResponse.setTotalElemenets(office.getTotalElements());
		officeResponse.setTotalPages(office.getTotalPages());
		officeResponse.setLast(office.isLast());
		return officeResponse;
	}
 
	

	@Override
	public Office getOffice(int id) {
		return  officeDao.findById(id).orElseThrow(() -> new ResourceNotFoundExcaption("Office", "id :", id) );
	}
	private Office checkIfOfficeExistsById(int id) {
		Office office = officeDao.findById(id).orElseThrow(() -> new ResourceNotFoundExcaption("Office", "id :", id));
		return office;
	}

	private Department checkIfDepartmentExistsById(int id) {
		Department department = departmentService.getDepartment(id);
		return department;
	}

}
