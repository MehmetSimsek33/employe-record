package macademia.employerecord.entities.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import macademia.employerecord.entities.dto.OfficeDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeResponse {
	
	private List<OfficeDto> content;
	private int pageNo;
	private int pageSize;
	private long totalElemenets;
	private int totalPages;
	private boolean last;
}
