package com.zee.systems.repository;


import com.zee.systems.entity.Deployment;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @dev : Ezekiel Eromosei
 * @date : 14 Oct, 2026
 */

@Repository
public interface DeploymentRepository extends ListCrudRepository<Deployment, Long> {
}
