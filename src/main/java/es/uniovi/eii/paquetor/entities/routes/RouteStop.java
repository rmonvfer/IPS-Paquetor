package es.uniovi.eii.paquetor.entities.routes;

import es.uniovi.eii.paquetor.entities.locations.Location;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Accessors(chain = true)
public class RouteStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public RouteStop() {
        setParcels(new ArrayList<>());
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RouteStopType type;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "route_stop_id", nullable = false)
    private List<Parcel> parcels;

    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private Location location;

    public RouteStop addParcel(Parcel parcel) {
        getParcels().add(parcel);
        return this;
    }
}