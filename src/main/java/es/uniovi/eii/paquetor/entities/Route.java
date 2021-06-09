package es.uniovi.eii.paquetor.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Accessors(chain = true)
public class Route {

    public Route() {
        this.routeStops = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "ROUTE_ID")
    @OneToMany(orphanRemoval = true)
    private List<RouteStop> routeStops;
}
