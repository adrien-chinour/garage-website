package frontend.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import frontend.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GarageApi implements Api<Garage> {

    private String url;

    @Autowired
    private ApiService apiService;

    private ObjectMapper mapper;

    public GarageApi(@Value("${api.baseUrl}") String url) {
        this.mapper = new ObjectMapper();
        this.url = url + "/garages";
    }

    @Override
    public Garage[] getAll() {
        return mapper.convertValue(apiService.getList(url, null), new TypeReference<Garage[]>() {
        });
    }

    @Override
    public Garage get(int id) {
        return mapper.convertValue(apiService.get(url + '/' + id, null), new TypeReference<Garage>() {
        });
    }

    @Override
    public Garage create(Garage entity) throws JsonProcessingException {
        Object data = apiService.post(url, mapper.writeValueAsString(entity));
        return mapper.convertValue(data, new TypeReference<Garage>() {
        });
    }

    @Override
    public Garage edit(Garage entity) throws JsonProcessingException {
        Object data = apiService.put(url + '/' + entity.getId(), mapper.writeValueAsString(entity));
        return mapper.convertValue(data, new TypeReference<Garage>() {
        });
    }

    @Override
    public Garage delete(int id) {
        return null;
    }
}
