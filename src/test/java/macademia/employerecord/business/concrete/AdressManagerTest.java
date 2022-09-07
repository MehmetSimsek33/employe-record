package macademia.employerecord.business.concrete;

import macademia.employerecord.business.abstracts.AdressService;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperManager;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperService;
import macademia.employerecord.dataAccess.abstracts.AdressDao;
import macademia.employerecord.entities.concrete.Adress;
import macademia.employerecord.entities.dto.AdressDto;
import macademia.employerecord.entities.dto.EmployeDto;
import macademia.employerecord.entities.response.AdressResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class AdressManagerTest {

    ModelMapperService modelMapperService;
    AdressService adressService;
    AdressDao adressDao;

    @BeforeEach
    void setUp() {
        modelMapperService = new ModelMapperManager(new ModelMapper());
        adressDao = mock(AdressDao.class);
        adressService= new AdressManager(adressDao,modelMapperService);
    }

    @Test
    void addAdress() {
        AdressDto adressDto= new AdressDto(1,"Mersin",null);
        AdressDto result = this.adressService.addAdress(adressDto);
        Assertions.assertEquals(adressDto,result);

    }
    @Test
    void  update() {
        Adress adress=new Adress();
        adress.setId(1);
        adress.setAdress("Manisa");
        adress.setDepartments(null);

        when(adressDao.findById(1)).thenReturn(Optional.of(adress));
        AdressDto adressDto= new AdressDto();
        adressDto.setId(1);
        adressDto.setAdress("Mersin");
        AdressDto result = this.adressService.updateAdress(adressDto,1);
        Assertions.assertEquals(adressDto,result);
    }
    @Test
    void getAdressById() {
        Adress adress=new Adress();
        adress.setId(1);
        adress.setAdress("Manisa");
        adress.setDepartments(null);
        when(adressDao.findById(1)).thenReturn(Optional.of(adress));
        AdressDto adressDto= modelMapperService.forResponse().map(adress,AdressDto.class);
     /*   AdressDto adressDto= new AdressDto();
        adressDto.setId(1);
        adressDto.setAdress("Manisa");*/
        AdressDto result = this.adressService.getAdressById(adressDto.getId());
        Assertions.assertEquals(adressDto,result);
    }

    @Test
    void  getAllAdress(){
        List<Adress> adresses = new ArrayList<>();
        Adress adress = new Adress();
        adress.setId(1);
        adress.setAdress("Mersin");
        adresses.add(adress);
        Page<Adress> pagedResponse = new PageImpl(adresses);
        Pageable paging = PageRequest.of(0, 10, Sort.by("id"));

        when(adressDao.findAll(paging)).thenReturn(pagedResponse);



        List<AdressDto> content= adresses.stream().map(adress1 -> modelMapperService.forResponse()
                .map(adress1,AdressDto.class)).collect(Collectors.toList());

        AdressResponse adressResponse = new AdressResponse();
        adressResponse.setContent(content);
        adressResponse.setPageNo(0);
        adressResponse.setPageSize(1);
        adressResponse.setTotalElemenets(1);
        adressResponse.setTotalPages(1);
        adressResponse.setLast(true);
        AdressResponse result = adressService.getAllAdress(0,10,"id","ASC");

        Assertions.assertEquals(adressResponse,result);
    }

}