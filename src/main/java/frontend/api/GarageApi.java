package frontend.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import frontend.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GarageApi implements Api<Garage> {

    @Autowired
    private ApiService apiService;

    private ObjectMapper mapper;

    private final String base = "/garages";

    public GarageApi() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public Garage[] get(@Nullable Map<String, String> filters) {
        return mapper.convertValue(apiService.get(base, filters), new TypeReference<Garage[]>() {
        });
    }

    @Override
    public Garage find(int id) {
        return mapper.convertValue(apiService.find(base + '/' + id), new TypeReference<Garage>() {
        });
    }

    @Override
    public Garage create(Garage entity) throws JsonProcessingException {
        Object data = apiService.post(base, mapper.writeValueAsString(entity));
        return mapper.convertValue(data, new TypeReference<Garage>() {
        });
    }

    @Override
    public Garage edit(Garage entity) throws JsonProcessingException {
        Object data = apiService.put(base + '/' + entity.getId(), mapper.writeValueAsString(entity));
        return mapper.convertValue(data, new TypeReference<Garage>() {
        });
    }

    @Override
    public void delete(int id) {
        apiService.delete(base + "/" + id);
    }
}
