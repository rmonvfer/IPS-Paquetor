package es.uniovi.eii.paquetor.entities.locations;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public City(String name) {
        setName(name);
    }

    public City() {
        setName("Indefinido");
    }
}
