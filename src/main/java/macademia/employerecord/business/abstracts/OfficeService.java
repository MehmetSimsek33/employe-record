package macademia.employerecord.business.abstracts;

import macademia.employerecord.entities.concrete.Office;
import macademia.employerecord.entities.dto.OfficeDto;
import macademia.employerecord.entities.response.OfficeResponse;

public interface OfficeService {
	
	OfficeDto addOffice(OfficeDto officeDto);

	OfficeDto getOfficeById(int id);

	OfficeDto updateOffice(OfficeDto officeDto, int id);

	void deleteOfficeById(int id);

	OfficeResponse getAllOffice(int pageNo, int pageSize, String sortBy, String sortDir);
	
	Office getOffice(int id);
	
}
