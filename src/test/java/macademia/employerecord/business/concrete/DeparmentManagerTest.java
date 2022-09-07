package macademia.employerecord.business.concrete;


import macademia.employerecord.business.abstracts.AdressService;
import macademia.employerecord.business.abstracts.DepartmentService;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperManager;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperService;
import macademia.employerecord.dataAccess.abstracts.AdressDao;
import macademia.employerecord.dataAccess.abstracts.DepartmentDao;
import macademia.employerecord.entities.concrete.Adress;
import macademia.employerecord.entities.concrete.Department;
import macademia.employerecord.entities.dto.AdressDto;
import macademia.employerecord.entities.dto.DepartmentDto;
import macademia.employerecord.entities.response.AdressResponse;
import macademia.employerecord.entities.response.DeparmentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class DeparmentManagerTest {

    ModelMapperService modelMapperService;
    DepartmentService departmentService;
    DepartmentDao departmentDao;
    AdressService adressService;

    @BeforeEach
    void setUp() {
        modelMapperService = new ModelMapperManager(new ModelMapper());
        departmentDao = mock(DepartmentDao.class);
        adressService=mock(AdressService.class);
        departmentService= new DeparmentManager(modelMapperService,departmentDao,adressService);
    }
    @Test
    void addDepartment() {
        DepartmentDto departmentDto= new DepartmentDto(1,"Finans",1,null,null);
        Adress adress=new Adress(1,"Mersin",null);
        Department context=this.modelMapperService.forRequest().map(departmentDto, Department.class);
        when(departmentDao.save(context)).thenReturn(context);
        when(adressService.getByAdress(1)).thenReturn(adress);
        DepartmentDto result = this.departmentService.addDepartment(departmentDto);
        Assertions.assertEquals(departmentDto,result);
    }
    @Test
    void  update() {

        Adress adress=new Adress(1,"Mersin",null);
        Department department=new Department(1,"Finans",null,adress,null);
        when(departmentDao.findById(1)).thenReturn(Optional.of(department));
        when(adressService.getByAdress(1)).thenReturn(adress);
        DepartmentDto departmentDto= new DepartmentDto(1,"Finans",1,null,null);

        DepartmentDto result = this.departmentService.updateDepartment(departmentDto,1);
        Assertions.assertEquals(departmentDto,result);
    }

    @Test
    void getDepartmentById() {
        Adress adress=new Adress(1,"Mersin",null);
        Department department=new Department(1,"Finans",null,adress,null);
        when(departmentDao.findById(1)).thenReturn(Optional.of(department));
        DepartmentDto departmentDto= modelMapperService.forResponse().map(department,DepartmentDto.class);
        DepartmentDto result = this.departmentService.getDeparmentById(departmentDto.getId());
        Assertions.assertEquals(departmentDto,result);
    }
    @Test
    void  getAllDepartments(){
        List<Department> departments = new ArrayList<>();
        Adress adress=new Adress(1,"Mersin",null);
        Department department=new Department(1,"Finans",null,adress,null);
        departments.add(department);
        Page<Department> pagedResponse = new PageImpl(departments);
        Pageable paging = PageRequest.of(0, 10, Sort.by("id"));

        when(departmentDao.findAll(paging)).thenReturn(pagedResponse);



        List<DepartmentDto> content= departments.stream().map(department1 -> modelMapperService.forResponse()
                .map(department1,DepartmentDto.class)).collect(Collectors.toList());

        DeparmentResponse deparmentResponse = new DeparmentResponse();
        deparmentResponse.setContent(content);
        deparmentResponse.setPageNo(0);
        deparmentResponse.setPageSize(1);
        deparmentResponse.setTotalElemenets(1);
        deparmentResponse.setTotalPages(1);
        deparmentResponse.setLast(true);
        DeparmentResponse result = departmentService.getAllDepartment(0,10,"id","ASC");

        Assertions.assertEquals(deparmentResponse,result);
    }


}