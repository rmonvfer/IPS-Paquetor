package es.uniovi.eii.paquetor.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {
    String [] roles = { "ROLE_USER", "ROLE_EMPLOYEE" };
    public String [] getRoles() {
        return this.roles;
    }
}