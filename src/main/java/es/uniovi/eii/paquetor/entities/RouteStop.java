package es.uniovi.eii.paquetor.entities;

import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter @Getter
public class RouteStop {
    @Id
    @GeneratedValue
    private UUID id;

    @JoinColumn(name = "PARCEL_ID", nullable = false)
    @OneToOne(optional = false, orphanRemoval = true)
    private Parcel parcel;
}