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

    public RouteStop() { /**/ }

    public RouteStop(Parcel parcel, RouteStopType stopType) {
        setParcel(parcel);
        setType(stopType);
    }

    @JoinColumn(name = "PARCEL_ID", nullable = false)
    @OneToOne(optional = false, orphanRemoval = true)
    private Parcel parcel;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private RouteStopType type;
}