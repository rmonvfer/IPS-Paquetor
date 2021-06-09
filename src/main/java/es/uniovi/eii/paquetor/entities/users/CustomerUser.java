package es.uniovi.eii.paquetor.entities.users;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Empleado usuario del sistema
 */
@Entity
@Data @Accessors(chain = true)
@DiscriminatorValue("customer_user")
public class CustomerUser extends BaseUser {

    public CustomerUser() {
        super();
        setRole();
    }

    public CustomerUser(String email) {
        super(email);
        setRole();
    }

    @Override
    void setRole() {
        super.role = "ROLE_USER";
    }
}
