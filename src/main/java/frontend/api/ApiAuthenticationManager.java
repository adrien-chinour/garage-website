package frontend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import frontend.model.Credentials;
import frontend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApiAuthenticationManager implements AuthenticationManager {

    @Autowired
    private ApiService apiService;

    @Autowired
    private UserApi userApi;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object username = authentication.getPrincipal();
        Object password = authentication.getCredentials();
        ObjectMapper mapper = new ObjectMapper();

        if (username != null && password != null) {
            try {
                String credentials = mapper.writeValueAsString(new Credentials(username, password));
                ResponseEntity<String> response =
                        apiService.sendRequest("/login", HttpMethod.POST, credentials, null);
                if (response.getStatusCode().is2xxSuccessful()) {
                    return getAuthentication(authentication);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private Authentication getAuthentication(Authentication authentication) {
        User user = userApi.getByUsername((String) authentication.getPrincipal());
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), getAuthorities(user));
    }


    private List<SimpleGrantedAuthority> getAuthorities(User user) {
        SimpleGrantedAuthority authority;

        if (user.getStatus().equals("partner")) {
            authority = new SimpleGrantedAuthority("ROLE_PARTNER");
        } else {
            authority = new SimpleGrantedAuthority("ROLE_MEMBER");
        }

        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<>();
        updatedAuthorities.add(authority);

        return updatedAuthorities;
    }
}
