package es.uniovi.eii.paquetor.entities.routes;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Accessors(chain = true)
public class Route {

    public Route() {
        setRouteStops(new ArrayList<>());
        setRouteType(RouteType.INTERNAL);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "route_id")
    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private List<RouteStop> routeStops;

    @Enumerated(EnumType.STRING)
    @Column(name = "route_type", nullable = false)
    private RouteType routeType;

    public Route addRouteStop(RouteStop routeStop) {
        getRouteStops().add(routeStop);
        return this;
    }
}
