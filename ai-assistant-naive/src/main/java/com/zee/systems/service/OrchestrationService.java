package com.zee.systems.service;


import com.zee.systems.client.DeploymentClient;
import com.zee.systems.client.HRClient;
import com.zee.systems.intent.ChatIntent;
import com.zee.systems.intent.IntentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @dev : Ezekiel Eromosei
 * @date : 19 Oct, 2026
 */

@Service
@RequiredArgsConstructor
public class OrchestrationService {

    private final HRClient hrClient;
    private final DeploymentClient deploymentClient;

    public String execute(ChatIntent intent) {
        if(intent.intent() == IntentType.REASSIGN_DEPLOYMENT_DUE_TO_LEAVE){
            hrClient.applyLeave(intent.username(), intent.date());

            String replacement = hrClient.findReplacement(intent.username(), intent.date());

            deploymentClient.reassignDeployments(intent.username(), replacement);
            return "Leave applied and deployments reassigned to " + replacement;
        }

        if(intent.intent() == IntentType.RESCHEDULE_DEPLOYMENT){
            deploymentClient.reschedule(intent.deploymentId(), intent.date());
            return "Deployment rescheduled successfully.";
        }

        return "Intent not recognized.";
    }
}
