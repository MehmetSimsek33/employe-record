package macademia.employerecord.entities.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import macademia.employerecord.entities.dto.EmploymentDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmploymentResponse {

    private List<EmploymentDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElemenets;
    private int totalPages;
    private boolean last;
}
