package es.uniovi.eii.paquetor.entities.locations;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data @Accessors(chain = true)
@DiscriminatorValue("home_location")
public class Home extends Location {
}