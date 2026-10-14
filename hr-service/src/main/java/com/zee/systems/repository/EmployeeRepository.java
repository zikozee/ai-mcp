package com.zee.systems.repository;


import com.zee.systems.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @dev : Ezekiel Eromosei
 * @date : 14 Oct, 2026
 */

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Optional<Employee> findByUsername(String username);

    List<Employee> findByTeam(String team);
}
