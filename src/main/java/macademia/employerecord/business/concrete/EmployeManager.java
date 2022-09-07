package macademia.employerecord.business.concrete;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import macademia.employerecord.business.abstracts.EmploymentService;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperService;
import macademia.employerecord.entities.concrete.Adress;
import macademia.employerecord.entities.concrete.Department;
import macademia.employerecord.entities.concrete.Employment;
import macademia.employerecord.entities.dto.DepartmentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import macademia.employerecord.business.abstracts.DepartmentService;
import macademia.employerecord.business.abstracts.EmployeService;
import macademia.employerecord.core.excaption.ResourceNotFoundExcaption;
import macademia.employerecord.dataAccess.abstracts.EmployeDao;
import macademia.employerecord.entities.concrete.Employe;
import macademia.employerecord.entities.dto.EmployeDto;
import macademia.employerecord.entities.response.EmployeResponse;

@Service
public class EmployeManager implements EmployeService {

    private DepartmentService departmentService;
    private EmployeDao employeDao;
    private ModelMapperService modelMapperService;
    private EmploymentService employmentService;

    @Autowired
    public EmployeManager(DepartmentService departmentService, EmployeDao employeDao, ModelMapperService modelMapperService, EmploymentService employmentService) {
        this.departmentService = departmentService;
        this.employeDao = employeDao;
        this.modelMapperService = modelMapperService;
        this.employmentService = employmentService;
    }

    @Override
    public EmployeDto addEmploye(EmployeDto employeDto) {
        checkIfDepartmentExistsById(employeDto.getDepartmentId());
        checkIfEmploymentExistsById(employeDto.getEmploymentId());
        Employe employe = this.modelMapperService.forRequest().map(employeDto, Employe.class);
        this.employeDao.save(employe);
        return employeDto;

    }

    @Override
    public EmployeDto getEmployeById(int id) {
        Employe employe = employeDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcaption("Department", "id", id));
        return this.modelMapperService.forResponse().map(employe, EmployeDto.class);
    }

    @Override
    public EmployeDto updateEmploye(EmployeDto employeDto, int id) {
        checkIfEmployeExistsById(id);
        checkIfDepartmentExistsById(employeDto.getDepartmentId());
        checkIfEmploymentExistsById(employeDto.getEmploymentId());
        Employe employe = this.modelMapperService.forRequest().map(employeDto, Employe.class);
        employe.setId(id);
        this.employeDao.save(employe);
        return employeDto;
    }

    @Override
    public void deleteEmployeById(int id) {
        Employe employe = checkIfEmployeExistsById(id);
        employeDao.delete(employe);

    }


    @Override
    public EmployeResponse getAllEmploye(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Employe> employe = employeDao.findAll(pageable);

        List<Employe> listOfEmploye = employe.getContent();
        List<EmployeDto> content = listOfEmploye.stream().map(employe1 -> this.modelMapperService.forResponse().map(employe1, EmployeDto.class))
                .collect(Collectors.toList());

        EmployeResponse employeResponse = new EmployeResponse();
        employeResponse.setContent(content);
        employeResponse.setPageNo(employe.getNumber());
        employeResponse.setPageSize(employe.getSize());
        employeResponse.setTotalElemenets(employe.getTotalElements());
        employeResponse.setTotalPages(employe.getTotalPages());
        employeResponse.setLast(employe.isLast());
        return employeResponse;
    }

    public Employe getWinnerSize() {
        List<Employe> listOfEmploye = this.employeDao.findAll();
        Random rand = new Random();
        int winnerSize = rand.nextInt(listOfEmploye.size());
        //System.out.println(listOfEmploye.get(winnerSize).getId());
        return getByEmployeId(listOfEmploye.get(winnerSize).getId());
    }

    public List<EmployeDto> getFilterSalary(int wage, LocalDate date) {
        List<Employe> listOfEmploye = this.employeDao.findAll();
        List<EmployeDto> listEncoKazanana = listOfEmploye.stream().map(list -> this.modelMapperService.forResponse().map(list, EmployeDto.class)).filter(t -> t.getWage() > wage && t.getStartDate().isAfter(date)).collect(Collectors.toList());
        return listEncoKazanana;

    }

    @Override
    public Employe getByEmployeId(int id) {
        return this.employeDao.findById(id).orElseThrow(() -> new ResourceNotFoundExcaption("Employe", "id :", id));
    }

    private Department checkIfDepartmentExistsById(int id) {
        Department department = departmentService.getDepartment(id);
        return department;
    }

    private Employment checkIfEmploymentExistsById(int id) {
        Employment employment = employmentService.checkIfEmploymentExistsById(id);
        return employment;
    }

    private Employe checkIfEmployeExistsById(int id) {
        Employe employe = employeDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcaption("Department", "id", id));
        return employe;
    }

}
