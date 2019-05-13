package frontend.api;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.ws.http.HTTPException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ApiService {

    List getList(String url) {
        ResponseEntity<String> response = sendRequest(url, HttpMethod.GET);
        Map<String, Object> data = new JacksonJsonParser().parseMap(response.getBody());
        return (List) data.get("data");
    }

    Object get(String url) {
        ResponseEntity<String> response = sendRequest(url, HttpMethod.GET);
        Map<String, Object> data = new JacksonJsonParser().parseMap(response.getBody());
        return data.get("data");
    }

    Object put(String url, String data) {
        ResponseEntity<String> response = sendRequest(url, HttpMethod.PUT);
        System.out.println(response.getBody());
        return null;
    }

    Object post(String url, String data) {
        ResponseEntity<String> response = sendRequest(url, HttpMethod.POST);
        System.out.println(response.getBody());
        return null;
    }

    /**
     * Send a request to an API endpoint.
     * @param url : endpoint
     * @return ResponseEntity with Body as String
     */
    private ResponseEntity<String> sendRequest(String url, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, method, entity, String.class);
    }
}
