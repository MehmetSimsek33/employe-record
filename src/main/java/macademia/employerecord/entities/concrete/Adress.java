package macademia.employerecord.entities.concrete;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "adress")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer","handler", "departments" })
public class Adress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "adress")
//    @NotEmpty(message = "Adress should not be null or empty")
//	@NotNull
	private String adress;

	
    @OneToMany(mappedBy = "adress")
    private List<Department> departments;
}
  