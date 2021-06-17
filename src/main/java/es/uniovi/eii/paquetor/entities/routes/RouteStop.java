package es.uniovi.eii.paquetor.entities.routes;

import es.uniovi.eii.paquetor.entities.locations.Location;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data @Accessors(chain = true)
public class RouteStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public RouteStop() {
        setParcels(new HashSet<>());
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RouteStopType type;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "route_stop_id")
    private Set<Parcel> parcels;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "visited", nullable = false)
    private boolean visited = false;

    public RouteStop addParcel(Parcel parcel) {
        getParcels().add(parcel);
        return this;
    }
}