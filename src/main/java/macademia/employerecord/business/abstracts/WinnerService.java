package macademia.employerecord.business.abstracts;

import macademia.employerecord.entities.dto.DepartmentDto;
import macademia.employerecord.entities.dto.WinnerDto;
import macademia.employerecord.entities.response.DeparmentResponse;
import macademia.employerecord.entities.response.WinnerResponse;

public interface WinnerService {
	WinnerDto addWinner(WinnerDto winnerDto);

	WinnerDto getWinnerById(int id);

	WinnerDto updateWinner(WinnerDto winnerDto, int id);

	void deleteWinnerById(int id);

	WinnerResponse getAllWinner(int pageNo, int pageSize, String sortBy, String sortDir);
}
