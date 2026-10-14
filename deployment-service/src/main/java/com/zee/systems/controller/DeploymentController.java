package com.zee.systems.controller;


import com.zee.systems.datatype.DeploymentEnvironment;
import com.zee.systems.entity.Deployment;
import com.zee.systems.service.DeploymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @dev : Ezekiel Eromosei
 * @date : 14 Oct, 2026
 */

@RestController
@RequestMapping(path = "deployments")
@RequiredArgsConstructor
public class DeploymentController {
    private final DeploymentService deploymentService;


    @GetMapping
    public ResponseEntity<List<Deployment>> getDeployments() {
        return ResponseEntity.ok(deploymentService.getDeployments());
    }


    @GetMapping(path = "{id}")
    public ResponseEntity<Deployment> getDeployment(@PathVariable Long id) {
        return ResponseEntity.ok(deploymentService.getDeployment(id));
    }


    @PostMapping
    public ResponseEntity<Deployment> createDeployment(@RequestParam String serviceName,
                                                       @RequestParam DeploymentEnvironment environment,
                                                       @RequestParam
                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                           LocalDateTime scheduledTime,
                                                       @RequestParam String owner) {

        return ResponseEntity.ok(deploymentService.createDeployment(serviceName, environment, scheduledTime, owner));
    }


    @PutMapping(path = "{id}/assign")
    public ResponseEntity<Deployment> assignDeployment(@PathVariable Long id, @RequestParam String assignee) {
        return ResponseEntity.ok(deploymentService.assignOwner(id, assignee));
    }

    @PutMapping(path = "{id}/reschedule")
    public ResponseEntity<Deployment> assignDeployment(@PathVariable Long id,
                                                       @RequestParam
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                       LocalDateTime newTime) {
        return ResponseEntity.ok(deploymentService.rescheduleDeployment(id, newTime));
    }

    @PutMapping(path = "{id}/cancel")
    public ResponseEntity<Deployment> cancelDeployment(@PathVariable Long id) {
        return ResponseEntity.ok(deploymentService.cancelDeployment(id));
    }
}
