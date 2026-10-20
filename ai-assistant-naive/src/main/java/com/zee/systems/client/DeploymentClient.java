package com.zee.systems.client;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * @dev : Ezekiel Eromosei
 * @date : 19 Oct, 2026
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class DeploymentClient {

    private final RestClient restClient;

    @Value("${services.deployment.base-url}")
    private String deploymentBaseUrl;

    public void reschedule(String deploymentId, String date){

        String url = deploymentBaseUrl + "/deployments/" + deploymentId + "/reschedule?newTime=" + date + "T10:00:00";
        restClient.put().uri(url);

    }

    public void reassignDeployments(String currentOwner, String newOwner){
        log.info("Re-assigning deployment to: {}", newOwner);

        String url = deploymentBaseUrl + "/deployments";

        DeploymentDto[] deployments = restClient.get().uri(url)
                .retrieve()
                .body(DeploymentDto[].class);

        for(DeploymentDto deployment : deployments){
            if(currentOwner.equalsIgnoreCase(deployment.owner())){
                String assignUrl = deploymentBaseUrl + "/deployments/" + deployment.id() +
                        "/assign?assignee=" + newOwner;

                restClient.put().uri(assignUrl);
            }
        }
    }
}
