package frontend.api;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ApiService {

    List getList(String url) {
        ResponseEntity<String> response = sendRequest(url, HttpMethod.GET, null);
        Map<String, Object> data = new JacksonJsonParser().parseMap(response.getBody());
        return (List) data.get("data");
    }

    Object get(String url) {
        ResponseEntity<String> response = sendRequest(url, HttpMethod.GET, null);
        Map<String, Object> data = new JacksonJsonParser().parseMap(response.getBody());
        return data.get("data");
    }

    Object put(String url, String entity) {
        ResponseEntity<String> response = sendRequest(url, HttpMethod.PUT, entity);
        return new JacksonJsonParser().parseMap(response.getBody());
    }

    Object post(String url, String entity) {
        ResponseEntity<String> response = sendRequest(url, HttpMethod.POST, entity);
        return new JacksonJsonParser().parseMap(response.getBody());
    }

    /**
     * Send a request to an API endpoint.
     *
     * @param url : endpoint
     * @return ResponseEntity with Body as String
     */
    private ResponseEntity<String> sendRequest(String url, HttpMethod method, @Nullable String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity;
        entity = body == null
                ? new HttpEntity<>(headers)
                : new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, method, entity, String.class);
    }
}
