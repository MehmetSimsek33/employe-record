package macademia.employerecord.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import macademia.employerecord.entities.concrete.Employe;

@Repository
public interface EmployeDao extends JpaRepository<Employe, Integer> {

}
