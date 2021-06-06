package es.uniovi.eii.paquetor.entities.users;

import es.uniovi.eii.paquetor.entities.locations.Location;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Empleado usuario del sistema
 */
@Entity
@Setter @Getter
public class CustomerUser extends BaseUser {

    public CustomerUser() {
        super();
    }

    public CustomerUser(String email) {
        super(email);
        setRole();
    }

    @Column(name="role", nullable = false)
    private String role;

    @JoinColumn(name = "LOCATION_ID")
    @ManyToOne
    private Location location;

    @Override
    void setRole() {
        this.role = "ROLE_USER";
    }
}
