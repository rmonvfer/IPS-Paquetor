package es.uniovi.eii.paquetor.entities;

import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RouteStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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