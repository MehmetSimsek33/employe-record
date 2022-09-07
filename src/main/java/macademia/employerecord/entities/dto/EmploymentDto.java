package macademia.employerecord.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import macademia.employerecord.entities.concrete.Employe;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentDto {

    @JsonIgnore
    private int id;

    @NotEmpty(message = "Employment should not be null or empty")
    private String employmentName;

    private List<Employe> employes;
}
