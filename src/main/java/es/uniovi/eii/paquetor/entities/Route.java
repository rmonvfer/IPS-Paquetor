package es.uniovi.eii.paquetor.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.UUID;

@Entity
@Setter @Getter
public class Route {
    @Id
    @GeneratedValue
    private UUID id;

    @JoinColumn(name = "ROUTE_ID", nullable = false)
    @OneToMany(orphanRemoval = true)
    private LinkedList<RouteStop> routeStops;
}
