package es.uniovi.eii.paquetor.entities.users;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Empleado transportista.
 */
@Entity
@Data
@Accessors(chain = true)
@DiscriminatorValue("employee_user")
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
