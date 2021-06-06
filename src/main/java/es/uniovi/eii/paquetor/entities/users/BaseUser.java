package es.uniovi.eii.paquetor.entities.users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity @Setter @Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseUser {

    public BaseUser(){ /**/ }

    public BaseUser(String email) {
        this.email = email;
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    abstract void setRole();
}