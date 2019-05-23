package frontend.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

interface Api<T> {

    T[] get(Map<String, String> filters);

    T find(int id);

    T create(T entity) throws JsonProcessingException;

    T edit(T entity) throws JsonProcessingException;

    void delete(int id);
}
