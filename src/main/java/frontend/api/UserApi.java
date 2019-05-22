package frontend.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import frontend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserApi implements Api<User> {

    private String url;

    @Autowired
    private ApiService apiService;

    private ObjectMapper mapper;

    public UserApi(@Value("${api.baseUrl}") String url) {
        this.mapper = new ObjectMapper();
        this.url = url + "/users";
    }

    @Override
    public User[] get(@Nullable Map<String,String> filters) {
        return mapper.convertValue(apiService.get(url, filters), new TypeReference<User[]>() {
        });
    }

    @Override
    public User find(int id) {
        return mapper.convertValue(apiService.get(url + '/' + id, null), new TypeReference<User>() {
        });
    }

    public User getByUsername(String username) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        User[] users = mapper.convertValue(apiService.get(url, params), new TypeReference<User[]>() {
        });

        return users.length == 1 ? users[0] : null;
    }

    @Override
    public User create(User entity) throws JsonProcessingException {
        Object data = apiService.post(url, mapper.writeValueAsString(entity));
        return mapper.convertValue(data, new TypeReference<User>() {
        });
    }

    @Override
    public User edit(User entity) throws JsonProcessingException {
        Object data = apiService.put(url + '/' + entity.getId(), mapper.writeValueAsString(entity));
        return mapper.convertValue(data, new TypeReference<User>() {
        });
    }

    @Override
    public User delete(int id) {
        return null;
    }
}
