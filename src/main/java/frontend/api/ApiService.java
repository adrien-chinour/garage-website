package frontend.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ApiService {

    private String url;

    public ApiService(@Value("${api.baseUrl}") String url) {
        this.url = url;
    }

    List get(String endpoint, Map<String,String> params) {
        ResponseEntity<String> response = sendRequest(endpoint, HttpMethod.GET, null, params);
        Map<String, Object> data = new JacksonJsonParser().parseMap(response.getBody());
        return (List) data.get("data");
    }

    Object find(String endpoint) {
        ResponseEntity<String> response = sendRequest(endpoint, HttpMethod.GET, null, null);
        Map<String, Object> data = new JacksonJsonParser().parseMap(response.getBody());
        return data.get("data");
    }

    Object put(String endpoint, String body) {
        ResponseEntity<String> response = sendRequest(endpoint, HttpMethod.PUT, body, null);
        return new JacksonJsonParser().parseMap(response.getBody());
    }

    Object post(String endpoint, String body) {
        ResponseEntity<String> response = sendRequest(endpoint, HttpMethod.POST, body, null);
        return new JacksonJsonParser().parseMap(response.getBody());
    }

    ResponseEntity<String> sendRequest(String endpoint, HttpMethod method, @Nullable String body, @Nullable Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity;
        ResponseEntity<String> response;

        entity = body == null
                ? new HttpEntity<>(headers)
                : new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor("Admin", "Admin"));
        response = params == null
                ? restTemplate.exchange(this.url + endpoint, method, entity, String.class)
                : restTemplate.exchange(this.url + endpoint, method, entity, String.class, params);

        return  response;
    }
}
