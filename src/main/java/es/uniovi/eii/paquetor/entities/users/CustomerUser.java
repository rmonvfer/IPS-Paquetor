package es.uniovi.eii.paquetor.entities.users;

import es.uniovi.eii.paquetor.entities.locations.Location;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Empleado usuario del sistema
 */
@Entity
@Data @Accessors(chain = true)
public class CustomerUser extends BaseUser {

    public CustomerUser() {
        super();
        setRole();
    }

    public CustomerUser(String email) {
        super(email);
        setRole();
    }

    @JoinColumn(name = "LOCATION_ID")
    @ManyToOne
    private Location location;

    @Override
    void setRole() {
        super.role = "ROLE_USER";
    }
}
