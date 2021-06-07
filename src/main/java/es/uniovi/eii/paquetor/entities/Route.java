package es.uniovi.eii.paquetor.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "ROUTE_ID", nullable = false)
    @OneToMany(orphanRemoval = true)
    private List<RouteStop> routeStops;
}
