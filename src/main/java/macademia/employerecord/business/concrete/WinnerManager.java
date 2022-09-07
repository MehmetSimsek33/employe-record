package macademia.employerecord.business.concrete;

import macademia.employerecord.core.excaption.ResourceNotFoundExcaption;
import macademia.employerecord.core.excaption.utilities.mapping.ModelMapperService;
import macademia.employerecord.entities.concrete.Employe;
import macademia.employerecord.entities.response.WinnerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import macademia.employerecord.business.abstracts.EmployeService;
import macademia.employerecord.business.abstracts.WinnerService;
import macademia.employerecord.dataAccess.abstracts.WinnerDao;
import macademia.employerecord.entities.concrete.Winner;

import macademia.employerecord.entities.dto.WinnerDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WinnerManager implements WinnerService {

	private ModelMapperService modelMapperService;
	private EmployeService employeService;
	private WinnerDao winnerDao;

	@Autowired
	public WinnerManager(ModelMapperService modelMapperService, EmployeService employeService, WinnerDao winnerDao) {
		this.modelMapperService = modelMapperService;
		this.employeService = employeService;
		this.winnerDao = winnerDao;
	}


	@Override
	public WinnerDto addWinner(WinnerDto winnerDto) {
		checkIfEmployeExistsById(winnerDto.getEmployeId());
		Winner winner = this.modelMapperService.forRequest().map(winnerDto, Winner.class);
		winner.setEmploye(employeService.getWinnerSize());
		this.winnerDao.save(winner);
		WinnerDto winnerResponse = this.modelMapperService.forRequest().map(winner, WinnerDto.class);
		return winnerResponse;
	}

	@Override
	public WinnerDto getWinnerById(int id) {
		Winner winner = checkIfWinnerExistsById(id);
		return this.modelMapperService.forResponse().map(winner, WinnerDto.class);
	}

	@Override
	public WinnerDto updateWinner(WinnerDto winnerDto, int id) {
		checkIfWinnerExistsById(id);
		checkIfEmployeExistsById(winnerDto.getEmployeId());
		Winner winner = this.modelMapperService.forRequest().map(winnerDto, Winner.class);
		winner.setId(id);
		this.winnerDao.save(winner);
		return winnerDto;
	}

	@Override
	public void deleteWinnerById(int id) {
		Winner winner = checkIfWinnerExistsById(id);
		winnerDao.delete(winner);
	}

	@Override
	public WinnerResponse getAllWinner(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Winner> winners = winnerDao.findAll(pageable);

		List<Winner> listOfWinner = winners.getContent();
		List<WinnerDto> content = listOfWinner.stream().map(winner1 -> this.modelMapperService.forResponse().map(winner1, WinnerDto.class))
				.collect(Collectors.toList());

		WinnerResponse winnerResponse = new WinnerResponse();
		winnerResponse.setContent(content);
		winnerResponse.setPageNo(winners.getNumber());
		winnerResponse.setPageSize(winners.getSize());
		winnerResponse.setTotalElemenets(winners.getTotalElements());
		winnerResponse.setTotalPages(winners.getTotalPages());
		winnerResponse.setLast(winners.isLast());
		return winnerResponse;
	}

	private Winner checkIfWinnerExistsById(int id) {
		Winner winner = winnerDao.findById(id).orElseThrow(() -> new ResourceNotFoundExcaption("Winner", "id :", id));
		return winner;
	}

	private Employe checkIfEmployeExistsById(int id) {
		Employe employe = employeService.getByEmployeId(id);
		return employe;
	}
}
