package macademia.employerecord.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import macademia.employerecord.entities.concrete.Office;

@Repository

public interface OfficeDao extends JpaRepository<Office, Integer> {
	

}
