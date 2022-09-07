package macademia.employerecord.business.concrete;

import java.util.List;
import java.util.stream.Collectors;

import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import macademia.employerecord.business.abstracts.AdressService;
import macademia.employerecord.business.abstracts.DepartmentService;
import macademia.employerecord.business.abstracts.OfficeService;
import macademia.employerecord.core.excaption.ResourceNotFoundExcaption;
import macademia.employerecord.dataAccess.abstracts.DepartmentDao;
import macademia.employerecord.entities.concrete.Adress;
import macademia.employerecord.entities.concrete.Department;
import macademia.employerecord.entities.dto.AdressDto;
import macademia.employerecord.entities.dto.DepartmentDto;
import macademia.employerecord.entities.response.AdressResponse;
import macademia.employerecord.entities.response.DeparmentResponse;

@Service
public class DeparmentManager implements DepartmentService {


    private ModelMapperService modelMapperService;
    private DepartmentDao departmentDao;
    private AdressService adressService;

    @Autowired
    public DeparmentManager(ModelMapperService modelMapperService, DepartmentDao departmentDao, AdressService adressService) {
        this.modelMapperService = modelMapperService;
        this.departmentDao = departmentDao;
        this.adressService = adressService;
    }



    @Override
    public DepartmentDto addDepartment(DepartmentDto departmentDto) {
        checkIfAdressExistsById(departmentDto.getAdressId());
        Department department = this.modelMapperService.forRequest().map(departmentDto, Department.class);
        this.departmentDao.save(department);
        return departmentDto;
    }

    @Override
    public DepartmentDto getDeparmentById(int id) {
        Department department = checkIfDepartmentExistsById(id);
        return this.modelMapperService.forResponse().map(department, DepartmentDto.class);

    }

    @Override
    public DepartmentDto updateDepartment(DepartmentDto departmentDto, int id) {
        checkIfDepartmentExistsById(id);
        checkIfAdressExistsById(departmentDto.getAdressId());
        Department department = this.modelMapperService.forRequest().map(departmentDto, Department.class);
        department.setId(id);
        this.departmentDao.save(department);
        return departmentDto;

    }

    @Override
    public void deleteDepartmenById(int id) {
        Department department = checkIfDepartmentExistsById(id);
        departmentDao.delete(department);
    }

    @Override
    public DeparmentResponse getAllDepartment(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Department> department = departmentDao.findAll(pageable);

        List<Department> listOfDepartment = department.getContent();
        List<DepartmentDto> content = listOfDepartment.stream().map(departmentt -> this.modelMapperService.forResponse().map(departmentt, DepartmentDto.class))
                .collect(Collectors.toList());

        DeparmentResponse deparmentResponse = new DeparmentResponse();
        deparmentResponse.setContent(content);
        deparmentResponse.setPageNo(department.getNumber());
        deparmentResponse.setPageSize(department.getSize());
        deparmentResponse.setTotalElemenets(department.getTotalElements());
        deparmentResponse.setTotalPages(department.getTotalPages());
        deparmentResponse.setLast(department.isLast());
        return deparmentResponse;

    }


    @Override
    public Department getDepartment(int id) {
        return checkIfDepartmentExistsById(id);
    }

    private Department checkIfDepartmentExistsById(int id) {
        Department department = departmentDao.findById(id).orElseThrow(() -> new ResourceNotFoundExcaption("Department", "id :", id));
        return department;
    }

    private Adress checkIfAdressExistsById(int id) {
        Adress adress = adressService.getByAdress(id);
        return adress;
    }

}
