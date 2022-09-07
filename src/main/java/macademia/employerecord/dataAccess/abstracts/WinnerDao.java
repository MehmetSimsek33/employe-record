package macademia.employerecord.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import macademia.employerecord.entities.concrete.Winner;

@Repository
public interface WinnerDao extends JpaRepository<Winner, Integer> {

}
