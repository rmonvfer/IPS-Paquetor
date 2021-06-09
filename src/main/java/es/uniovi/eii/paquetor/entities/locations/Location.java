package es.uniovi.eii.paquetor.entities.locations;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.UUID;

/**
 * Define una Ubicación en el mapa
 */
@Entity
@Data
@Accessors(chain = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "location_type")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CALLE", nullable = false)
    private String calle;

    @Column(name = "NUMERO", nullable = false)
    private int numero;

    @Column(name = "PISO")
    private String piso;

    @Column(name = "PUERTA")
    private String puerta;

    @Column(name = "CIUDAD", nullable = false)
    private String ciudad;

    @Column(name = "CODIGO_POSTAL", nullable = false)
    private int codigoPostal;
}
