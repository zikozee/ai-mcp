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
public class HRClient {

    private final RestClient restClient;

    @Value("${services.hr.base-url}")
    private String hrBaseUrl;

    public void applyLeave(String username, String date){
        log.info("Applying leave for: {}", username);
        String url = hrBaseUrl + "/hr/leave?username=" + username + "&date=" + date;
        String body = restClient.post().uri(url)
                .retrieve()
                .body(String.class);

        log.info("body: {}", body);
    }

    public String findReplacement(String username, String date){
        log.info("Finding Replacement for: {}", username);
        String url = hrBaseUrl + "/hr/replacement/" + username + "?date=" + date;
        return restClient.get().uri(url)
                .retrieve()
                .body(String.class);
    }
}
