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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="offices")
public class Office  {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")	
	private int id;
	
	@Column(name="office_name")	
	private String officeName;

	@Column(name="office_phone")	
	private String officePhone;
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	
}