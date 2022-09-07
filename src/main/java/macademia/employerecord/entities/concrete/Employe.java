package macademia.employerecord.entities.concrete;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "employes")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "payrolls" })
public class Employe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

//	@Column(name = "birth_date")
//	private LocalDate birthDate;

	@Column(name = "start_date")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
	private LocalDate startDate;

	@Column(name = "wage")
	private int wage;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	@ManyToOne
	@JoinColumn(name = "employment_id")
	private Employment employment;

}