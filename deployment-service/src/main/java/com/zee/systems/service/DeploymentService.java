package com.zee.systems.service;


import com.zee.systems.datatype.DeploymentEnvironment;
import com.zee.systems.datatype.DeploymentStatus;
import com.zee.systems.entity.Deployment;
import com.zee.systems.repository.DeploymentRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @dev : Ezekiel Eromosei
 * @date : 14 Oct, 2026
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class DeploymentService {

    private final DeploymentRepository deploymentRepository;

    @Transactional(readOnly = true)
    public List<Deployment> getDeployments(){
        return deploymentRepository.findAll();
    }

    public Deployment getDeployment(Long id){
        return deploymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Deployment not found"));
    }


    @Transactional
    public Deployment createDeployment(String serviceName, DeploymentEnvironment deploymentEnvironment, LocalDateTime scheduledTime,
                                       String owner){

        return deploymentRepository
                .save(
                        Deployment.builder()
                                .serviceName(serviceName)
                                .environment(deploymentEnvironment)
                                .scheduledTime(scheduledTime)
                                .status(DeploymentStatus.SCHEDULED)
                                .owner(owner)
                                .createdAt(LocalDateTime.now(ZoneId.systemDefault()))
                                .updatedAt(LocalDateTime.now(ZoneId.systemDefault()))
                                .build()
                );
    }


    @Transactional
    public Deployment assignOwner(Long id, String newOwner){
        Deployment deployment = getDeployment(id);

        deployment.setOwner(newOwner);
        deployment.setUpdatedAt(LocalDateTime.now(ZoneId.systemDefault()));

        Deployment savedDeployment = deploymentRepository.save(deployment);
        log.info("Assign owner to {} for deployment with id: {}", newOwner, savedDeployment.getId());
        return savedDeployment;
    }


    @Transactional
    public Deployment rescheduleDeployment(Long id, LocalDateTime newTime){
        Deployment deployment = getDeployment(id);
        deployment.setScheduledTime(newTime);
        deployment.setUpdatedAt(LocalDateTime.now(ZoneId.systemDefault()));
        return deploymentRepository.save(deployment);
    }

    @Transactional
    public Deployment cancelDeployment(Long id){
        Deployment deployment = getDeployment(id);
        deployment.setStatus(DeploymentStatus.CANCELLED);
        deployment.setUpdatedAt(LocalDateTime.now(ZoneId.systemDefault()));
        return deploymentRepository.save(deployment);
    }
}
