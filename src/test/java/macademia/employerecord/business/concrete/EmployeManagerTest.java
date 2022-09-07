package macademia.employerecord.business.concrete;

import macademia.employerecord.business.abstracts.AdressService;
import macademia.employerecord.business.abstracts.DepartmentService;
import macademia.employerecord.business.abstracts.EmployeService;
import macademia.employerecord.business.abstracts.EmploymentService;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperManager;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperService;
import macademia.employerecord.dataAccess.abstracts.DepartmentDao;
import macademia.employerecord.dataAccess.abstracts.EmployeDao;
import macademia.employerecord.entities.concrete.Adress;
import macademia.employerecord.entities.concrete.Department;
import macademia.employerecord.entities.concrete.Employe;
import macademia.employerecord.entities.concrete.Employment;
import macademia.employerecord.entities.dto.DepartmentDto;
import macademia.employerecord.entities.dto.EmployeDto;
import macademia.employerecord.entities.dto.EmploymentDto;
import macademia.employerecord.entities.response.DeparmentResponse;
import macademia.employerecord.entities.response.EmployeResponse;
import macademia.employerecord.entities.response.EmploymentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeManagerTest {

    DepartmentService departmentService;
    EmployeDao employeDao;
    ModelMapperService modelMapperService;
    EmploymentService employmentService;
    EmployeService employeService;

    @BeforeEach
    void setUp() {
        modelMapperService = new ModelMapperManager(new ModelMapper());
        employeDao = mock(EmployeDao.class);
        departmentService = mock(DepartmentService.class);
        employmentService = mock(EmploymentService.class);
        employeService = new EmployeManager(departmentService, employeDao, modelMapperService, employmentService);
    }

    @Test
    void addEmployment() {
        EmployeDto employeDto = new EmployeDto(1, "Mehmet", "Simsek", "mehmetsimsek3345@gmail.com",
                                                LocalDate.parse("2021-10-11"), 5000, 1, 1);
        Department department = new Department(1, "Finans", null, null, null);
        Employment employment = new Employment(1, "Hybrid", null);
        Employe context = this.modelMapperService.forRequest().map(employeDto, Employe.class);
        when(employeDao.save(context)).thenReturn(context);
        when(departmentService.getDepartment(1)).thenReturn(department);
        when(employmentService.checkIfEmploymentExistsById(1)).thenReturn(employment);
        EmployeDto result = this.employeService.addEmploye(employeDto);
        Assertions.assertEquals(employeDto, result);
    }

    @Test
    void update() {
        Department department = new Department(1, "Finans", null, null, null);
        Employment employment = new Employment(1, "Hybrid", null);
        Employe employe = new Employe(1, "Mehmet", "Simsek", "mehmetsimsek3345@gmail.com",
                                        LocalDate.parse("2021-10-11"), 5000, department, employment);

        when(employeDao.findById(1)).thenReturn(Optional.of(employe));
        when(departmentService.getDepartment(1)).thenReturn(department);
        when(employmentService.checkIfEmploymentExistsById(1)).thenReturn(employment);
        EmployeDto employeDto = new EmployeDto(1, "Fatih", "Simsek", "mehmetsimsek3345@gmail.com",
                LocalDate.parse("2021-10-11"), 5000, 1, 1);

        EmployeDto result = this.employeService.updateEmploye(employeDto, 1);
        Assertions.assertEquals(employeDto, result);
    }

    @Test
    void getEmployeById() {
        Department department = new Department(1, "Finans", null, null, null);
        Employment employment = new Employment(1, "Hybrid", null);
        Employe employe = new Employe(1, "Mehmet", "Simsek", "mehmetsimsek3345@gmail.com",
                LocalDate.parse("2021-10-11"), 5000, department, employment);
        when(employeDao.findById(1)).thenReturn(Optional.of(employe));
        EmployeDto employeDto= modelMapperService.forResponse().map(employe,EmployeDto.class);

        EmployeDto result = this.employeService.getEmployeById(employe.getId());
        Assertions.assertEquals(employeDto,result);
    }
    @Test
    void  getAllEmployes(){
        List<Employe> employes = new ArrayList<>();
        Department department = new Department(1, "Finans", null, null, null);
        Employment employment = new Employment(1, "Hybrid", null);
        Employe employe = new Employe(1, "Mehmet", "Simsek", "mehmetsimsek3345@gmail.com",
                LocalDate.parse("2021-10-11"), 5000, department, employment);
        employes.add(employe);
        Page<Employe> pagedResponse = new PageImpl(employes);
        Pageable paging = PageRequest.of(0, 10, Sort.by("id"));

        when(employeDao.findAll(paging)).thenReturn(pagedResponse);



        List<EmployeDto> content= employes.stream().map(employe1 -> modelMapperService.forResponse()
                .map(employe1,EmployeDto.class)).collect(Collectors.toList());

        EmployeResponse employeResponse = new EmployeResponse();
        employeResponse.setContent(content);
        employeResponse.setPageNo(0);
        employeResponse.setPageSize(1);
        employeResponse.setTotalElemenets(1);
        employeResponse.setTotalPages(1);
        employeResponse.setLast(true);
        EmployeResponse result = employeService.getAllEmploye(0,10,"id","ASC");

        Assertions.assertEquals(employeResponse,result);
    }

    @Test
    void getFilterSalary()
    {

        List<Employe> employes = new ArrayList<>();
        Department department = new Department(1, "Finans", null, null, null);
        Employment employment = new Employment(1, "Hybrid", null);
        Employe employe = new Employe(1, "Mehmet", "Simsek", "mehmetsimsek3345@gmail.com",
                LocalDate.parse("2021-10-11"), 5000, department, employment);
        employes.add(employe);
        when(employeDao.findAll()).thenReturn(employes);
        List<EmployeDto> content= employes.stream().map(employe1 -> modelMapperService.forResponse()
                .map(employe1,EmployeDto.class)).collect(Collectors.toList());

        List<EmployeDto> result = employeService.getFilterSalary(2000, LocalDate.parse("2021-10-10") );

        Assertions.assertEquals(content,result);

    }

}