package es.uniovi.eii.paquetor.entities.users;
import es.uniovi.eii.paquetor.entities.locations.Location;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Accessors(chain = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public abstract class BaseUser {

    public BaseUser(){ /**/ }

    public BaseUser(String email) {
        this.email = email;
    }

    @Id
    @Type(type = "uuid-char")
    private UUID id;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(length = 64)
    private String password;

    @Column(nullable = false, length = 64)
    private String DNI;

    @Column(name="role", nullable = false)
    protected String role;

    @JoinColumn(name = "LOCATION_ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Location location;

    @Transient
    private String passwordConfirm;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    abstract void setRole();
}