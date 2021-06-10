package es.uniovi.eii.paquetor.entities.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Empleado usuario del sistema
 */
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Accessors(chain = true)
@DiscriminatorValue("customer_user")
public class CustomerUser extends BaseUser {

    public CustomerUser(String email) {
        super(email);
        setRole();
    }

    @Override
    void setRole() {
        super.role = "ROLE_USER";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomerUser that = (CustomerUser) o;

        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return 1626099339;
    }

    @Override
    public String toString() {
        return String.format(
                "CustomerUser( id=%s, email=%s, password=%s, passwordConfirm=%s )",
                getId(), getEmail(), getPassword(), getPasswordConfirm());
    }
}
