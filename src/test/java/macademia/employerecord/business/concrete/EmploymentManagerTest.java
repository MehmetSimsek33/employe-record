package macademia.employerecord.business.concrete;

import macademia.employerecord.business.abstracts.EmploymentService;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperManager;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperService;
import macademia.employerecord.dataAccess.abstracts.AdressDao;
import macademia.employerecord.dataAccess.abstracts.EmploymentDao;
import macademia.employerecord.entities.concrete.Adress;
import macademia.employerecord.entities.concrete.Department;
import macademia.employerecord.entities.concrete.Employment;
import macademia.employerecord.entities.dto.AdressDto;
import macademia.employerecord.entities.dto.DepartmentDto;
import macademia.employerecord.entities.dto.EmploymentDto;
import macademia.employerecord.entities.response.DeparmentResponse;
import macademia.employerecord.entities.response.EmploymentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmploymentManagerTest {

    ModelMapperService modelMapperService;
    EmploymentService employmentService;
    EmploymentDao employmentDao;

    @BeforeEach
    void setUp() {
        modelMapperService = new ModelMapperManager(new ModelMapper());
        employmentDao = mock(EmploymentDao.class);
        employmentService = new EmploymentManager(modelMapperService, employmentDao);
    }
    @Test
    void addEmployment() {
        EmploymentDto employmentDto= new EmploymentDto(1,"Hybrid",null);
        EmploymentDto result = this.employmentService.addEmployment(employmentDto);
        Assertions.assertEquals(employmentDto,result);

    }
    @Test
    void  update() {
        Employment employment=new Employment(1,"Hybrid",null);
        when(employmentDao.findById(1)).thenReturn(Optional.of(employment));
        EmploymentDto employmentDto= new EmploymentDto(1,"Remote",null);
        EmploymentDto result = this.employmentService.updateEmployment(employmentDto,1);
        Assertions.assertEquals(employmentDto,result);
    }
    @Test
    void getEmploymentById() {
        Employment employment=new Employment(1,"Hybrid",null);
        when(employmentDao.findById(1)).thenReturn(Optional.of(employment));
        EmploymentDto employmentDto= new EmploymentDto(1,"Hybrid",null);
        EmploymentDto result = this.employmentService.getEmploymentById(employmentDto.getId());
        Assertions.assertEquals(employmentDto,result);
    }

    @Test
    void  getAllEmployments(){
        List<Employment> employments = new ArrayList<>();
        Employment employment=new Employment(1,"Hybrid",null);
        employments.add(employment);
        Page<Employment> pagedResponse = new PageImpl(employments);
        Pageable paging = PageRequest.of(0, 10, Sort.by("id"));

        when(employmentDao.findAll(paging)).thenReturn(pagedResponse);



        List<EmploymentDto> content= employments.stream().map(employment1 -> modelMapperService.forResponse()
                .map(employment1,EmploymentDto.class)).collect(Collectors.toList());

        EmploymentResponse employmentResponse = new EmploymentResponse();
        employmentResponse.setContent(content);
        employmentResponse.setPageNo(0);
        employmentResponse.setPageSize(1);
        employmentResponse.setTotalElemenets(1);
        employmentResponse.setTotalPages(1);
        employmentResponse.setLast(true);
        EmploymentResponse result = employmentService.getAllEmployment(0,10,"id","ASC");

        Assertions.assertEquals(employmentResponse,result);
    }

}