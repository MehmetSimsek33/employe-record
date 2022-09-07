package macademia.employerecord.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import macademia.employerecord.entities.concrete.Adress;

@Repository
public interface AdressDao extends JpaRepository<Adress, Integer> {

}
