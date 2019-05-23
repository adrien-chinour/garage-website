package frontend.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import frontend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final String base = "/users";

    public UserApi() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public User[] get(@Nullable Map<String,String> filters) {
        return mapper.convertValue(apiService.get(base, filters), new TypeReference<User[]>() {
        });
    }

    @Override
    public User find(int id) {
        return mapper.convertValue(apiService.get(base + '/' + id, null), new TypeReference<User>() {
        });
    }

    public User getByUsername(String username) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        User[] users = mapper.convertValue(apiService.get(base, params), new TypeReference<User[]>() {
        });

        return users.length == 1 ? users[0] : null;
    }

    @Override
    public User create(User entity) throws JsonProcessingException {
        Object data = apiService.post(base, mapper.writeValueAsString(entity));
        return mapper.convertValue(data, new TypeReference<User>() {
        });
    }

    @Override
    public User edit(User entity) throws JsonProcessingException {
        Object data = apiService.put(base + '/' + entity.getId(), mapper.writeValueAsString(entity));
        return mapper.convertValue(data, new TypeReference<User>() {
        });
    }

    @Override
    public User delete(int id) {
        return null;
    }
}
