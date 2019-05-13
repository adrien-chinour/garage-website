package frontend.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import frontend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserApi implements Api<User> {

    private static final String url = "http://virtserver.swaggerhub.com/web-well/garageAPI/1.0.0/users";

    @Autowired
    private ApiService apiService;

    private ObjectMapper mapper;

    public UserApi() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public User[] getAll() {
        return mapper.convertValue(apiService.getList(url), new TypeReference<User[]>() {
        });
    }

    @Override
    public User get(int id) {
        return mapper.convertValue(apiService.get(url + '/' + id), new TypeReference<User>() {
        });
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
