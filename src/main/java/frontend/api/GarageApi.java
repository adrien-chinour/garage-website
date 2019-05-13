package frontend.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import frontend.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GarageApi implements Api<Garage> {

    private static final String url = "http://virtserver.swaggerhub.com/web-well/garageAPI/1.0.0/garages";

    @Autowired
    private ApiService apiService;

    private ObjectMapper mapper;

    public GarageApi() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public Garage[] getAll() {
        return mapper.convertValue(apiService.getList(url), new TypeReference<Garage[]>() {});
    }

    @Override
    public Garage get(int id) {
        return mapper.convertValue(apiService.get(url + '/' + id), new TypeReference<Garage>() {});
    }

    @Override
    public Garage create(Garage entity) {
        return null;
    }

    @Override
    public Garage edit(int id) {
        return null;
    }

    @Override
    public Garage delete(int id) {
        return null;
    }
}
