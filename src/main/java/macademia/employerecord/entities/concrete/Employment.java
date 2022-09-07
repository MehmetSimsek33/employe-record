package macademia.employerecord.entities.concrete;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "employes" })
@Table(name = "employments")
public class Employment {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Id
	private int id;

	@Column(name = "employment_name")
	private String employmentName;

	@OneToMany(mappedBy = "employment")
	private List<Employe> employes; 
}
