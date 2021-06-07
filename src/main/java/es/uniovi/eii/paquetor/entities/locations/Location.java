package es.uniovi.eii.paquetor.entities.locations;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

/**
 * Define una Ubicación en el mapa
 */
@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Location {

    @Id
    @GeneratedValue
    private UUID id;

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
