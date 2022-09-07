package macademia.employerecord.business.concrete;

import macademia.employerecord.business.abstracts.AdressService;
import macademia.employerecord.business.abstracts.EmploymentService;
import macademia.employerecord.core.excaption.ResourceNotFoundExcaption;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperService;
import macademia.employerecord.dataAccess.abstracts.DepartmentDao;
import macademia.employerecord.dataAccess.abstracts.EmploymentDao;
import macademia.employerecord.entities.concrete.Adress;
import macademia.employerecord.entities.concrete.Department;
import macademia.employerecord.entities.concrete.Employe;
import macademia.employerecord.entities.concrete.Employment;
import macademia.employerecord.entities.dto.AdressDto;
import macademia.employerecord.entities.dto.EmploymentDto;
import macademia.employerecord.entities.response.AdressResponse;
import macademia.employerecord.entities.response.EmploymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmploymentManager implements EmploymentService {

    private ModelMapperService modelMapperService;
    private EmploymentDao employmentDao;

    @Autowired
    public EmploymentManager(ModelMapperService modelMapperService, EmploymentDao employmentDao) {
        this.modelMapperService = modelMapperService;
        this.employmentDao = employmentDao;
    }



    @Override
    public EmploymentDto addEmployment(EmploymentDto employmentDto) {
        Employment employment = this.modelMapperService.forRequest().map(employmentDto, Employment.class);
        this.employmentDao.save(employment);
        return employmentDto;
    }

    @Override
    public EmploymentDto getEmploymentById(int id) {
        Employment employment= checkIfEmploymentExistsById(id);
        return this.modelMapperService.forResponse().map(employment, EmploymentDto.class);
    }


    @Override
    public EmploymentDto updateEmployment(EmploymentDto employmentDto, int id) {
        checkIfEmploymentExistsById(id);
        Employment employment = this.modelMapperService.forRequest().map(employmentDto, Employment.class);
        employment.setId(id);
        this.employmentDao.save(employment);
        return this.modelMapperService.forRequest().map(employment, EmploymentDto.class);
    }

    @Override
    public void deleteEmploymentById(int id) {
        Employment employment= checkIfEmploymentExistsById(id);
        employmentDao.delete(employment);
    }

    @Override
    public EmploymentResponse getAllEmployment(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Employment> employments = employmentDao.findAll(pageable);

        List<Employment> listOfAdress = employments.getContent();

        List<EmploymentDto> content = listOfAdress.stream().map(post ->this.modelMapperService.forResponse().map(post, EmploymentDto.class)).collect(Collectors.toList());


        EmploymentResponse employmentResponse = new EmploymentResponse();
        employmentResponse.setContent(content);
        employmentResponse.setPageNo(employments.getNumber());
        employmentResponse.setPageSize(employments.getSize());
        employmentResponse.setTotalElemenets(employments.getTotalElements());
        employmentResponse.setTotalPages(employments.getTotalPages());
        employmentResponse.setLast(employments.isLast());
        return employmentResponse;
    }
    public Employment checkIfEmploymentExistsById(int id) {
        Employment employment = employmentDao.findById(id).orElseThrow(() -> new ResourceNotFoundExcaption("Employment", "id :", id));
        return employment;
    }


}
