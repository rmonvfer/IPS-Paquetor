package es.uniovi.eii.paquetor.entities.users;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Empleado transportista.
 */
@Entity
@Getter @Setter
public class EmployeeUser extends BaseUser {

    public EmployeeUser() {
        super();
    }

    public EmployeeUser(String email) {
        super(email);
        setRole();
    }

    @Column(name="role", nullable = false)
    private String role;

    @Override
    void setRole() {
        this.role = "ROLE_EMPLOYEE";
    }
}
