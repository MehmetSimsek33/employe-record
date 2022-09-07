package macademia.employerecord.dataAccess.abstracts;

import macademia.employerecord.entities.concrete.Employment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmploymentDao  extends JpaRepository<Employment, Integer> {
}
