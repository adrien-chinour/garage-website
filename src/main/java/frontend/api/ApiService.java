package frontend.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ApiService {

    private String url;
    private String username;
    private String password;

    public ApiService(
            @Value("${api.baseUrl}") String url,
            @Value("${api.username}") String username,
            @Value("${api.password}") String password
    ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    List get(String endpoint, Map<String, String> params) {
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

    void delete(String endpoint) {
        sendRequest(endpoint, HttpMethod.DELETE, null, null);
    }

    ResponseEntity<String> sendRequest(String endpoint, HttpMethod method, @Nullable String body, @Nullable Map<String, String> params) {
        System.out.println("Request body :" + body);
        System.out.println("Request params :" + params);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity;
        ResponseEntity<String> response;

        if (body == null) {
            entity = new HttpEntity<>(headers);
        } else {
            entity = new HttpEntity<>(body, headers);
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url + endpoint);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue().replace(' ', '+'));
            }
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        System.out.println(builder.toUriString());
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor(this.username, this.password));
        response = restTemplate.exchange(builder.toUriString(), method, entity, String.class);
        return response;
    }
}
