package macademia.employerecord.business.concrete;

import macademia.employerecord.business.abstracts.AdressService;
import macademia.employerecord.business.abstracts.DepartmentService;
import macademia.employerecord.business.abstracts.OfficeService;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperManager;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperService;
import macademia.employerecord.dataAccess.abstracts.DepartmentDao;
import macademia.employerecord.dataAccess.abstracts.OfficeDao;
import macademia.employerecord.entities.concrete.Adress;
import macademia.employerecord.entities.concrete.Department;
import macademia.employerecord.entities.concrete.Office;
import macademia.employerecord.entities.dto.DepartmentDto;
import macademia.employerecord.entities.dto.OfficeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OfiiceManagerTest {
     ModelMapperService modelMapperService;
     OfficeDao officeDao;
     DepartmentService departmentService;
     OfficeService officeService;
    @BeforeEach
    void setUp() {
        modelMapperService = new ModelMapperManager(new ModelMapper());
        officeDao = mock(OfficeDao.class);
        departmentService=mock(DepartmentService.class);
        officeService= new OfiiceManager(modelMapperService,officeDao,departmentService);
    }

    @Test
    void addDepartment() {
        OfficeDto officeDto= new OfficeDto(1,"Yazılım","5346341179",1);
        Department department= new Department(1,"Finans",null,null,null);
        Office context=this.modelMapperService.forRequest().map(officeDto, Office.class);
        when(officeDao.save(context)).thenReturn(context);
        when(departmentService.getDepartment(1)).thenReturn(department);
        OfficeDto result = this.officeService.addOffice(officeDto);
        Assertions.assertEquals(officeDto,result);

    }
    @Test
    void  update() {
        Department department= new Department(1,"Finans",null,null,null);
        Office office= new Office(1,"Yazılım","5346341179",department);

        when(officeDao.findById(1)).thenReturn(Optional.of(office));
        when(departmentService.getDepartment(1)).thenReturn(department);
        OfficeDto officeDto= new OfficeDto(1,"Finans","5346341179",1);

        OfficeDto result = this.officeService.updateOffice(officeDto,1);
        Assertions.assertEquals(officeDto,result);
    }

    @Test
    void getOfficeById() {
        Department department= new Department(1,"Finans",null,null,null);
        Office office= new Office(1,"Yazılım","5346341179",department);
        when(officeDao.findById(1)).thenReturn(Optional.of(office));
        OfficeDto officeDto= modelMapperService.forResponse().map(office,OfficeDto.class);
        OfficeDto result = this.officeService.getOfficeById(officeDto.getId());
        Assertions.assertEquals(officeDto,result);
    }
}