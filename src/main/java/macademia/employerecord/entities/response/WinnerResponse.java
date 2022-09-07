package macademia.employerecord.entities.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import macademia.employerecord.entities.dto.WinnerDto;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WinnerResponse {
    private List<WinnerDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElemenets;
    private int totalPages;
    private boolean last;
}
