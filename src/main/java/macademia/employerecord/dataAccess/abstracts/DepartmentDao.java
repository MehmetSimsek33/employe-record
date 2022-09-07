package macademia.employerecord.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import macademia.employerecord.entities.concrete.Department;

@Repository
public interface DepartmentDao extends JpaRepository<Department, Integer>{

}
