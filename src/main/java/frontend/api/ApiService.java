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

    List get(String url, Map<String,String> params) {
        ResponseEntity<String> response = sendRequest(url, HttpMethod.GET, null, params);
        Map<String, Object> data = new JacksonJsonParser().parseMap(response.getBody());
        return (List) data.get("data");
    }

    Object find(String url) {
        ResponseEntity<String> response = sendRequest(url, HttpMethod.GET, null, null);
        Map<String, Object> data = new JacksonJsonParser().parseMap(response.getBody());
        return data.get("data");
    }

    Object put(String url, String body) {
        ResponseEntity<String> response = sendRequest(url, HttpMethod.PUT, body, null);
        return new JacksonJsonParser().parseMap(response.getBody());
    }

    Object post(String url, String body) {
        ResponseEntity<String> response = sendRequest(url, HttpMethod.POST, body, null);
        return new JacksonJsonParser().parseMap(response.getBody());
    }

    private ResponseEntity<String> sendRequest(String url, HttpMethod method, @Nullable String body, @Nullable Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity;
        ResponseEntity<String> response;

        entity = body == null
                ? new HttpEntity<>(headers)
                : new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        response = params == null
                ? restTemplate.exchange(url, method, entity, String.class)
                : restTemplate.exchange(url, method, entity, String.class, params);

        return  response;
    }
}
