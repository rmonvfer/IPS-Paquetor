package es.uniovi.paquetor.services;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class RolesService {
    HashMap<String, String> roles = new HashMap<>(2);

    @PostConstruct
    public void init() {
        roles.put("USER", "ROLE_USER");
        roles.put("EMPLOYEE", "ROLE_EMPLOYEE");
    }

    public HashMap<String, String> getRoles() {
        return roles;
    }
}

