package es.uniovi.eii.paquetor.entities.locations;

import es.uniovi.eii.paquetor.entities.User;
import es.uniovi.eii.paquetor.entities.routes.Route;
import es.uniovi.eii.paquetor.entities.locations.transferZone.WarehouseTransferZone;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Un almacén. Dispone de 3 elementos principales
 *  - Una ruta interna, cuyas paradas son ubicaciones en la propia ciudad
 *  - Una lista Almacenes, representando los destinos de las rutas externas (que salen del almacén actual).
 *  - Una zona de transferencia, donde se colocan los paquetes que llegan de la ruta externa hasta ser
 *    transferidos a la ruta interna.
 */
@Entity
@Data @Accessors(chain = true)
@DiscriminatorValue("warehouse_location")
public class Warehouse extends Location {

    public Warehouse() {
        setExternalRoutes(new ArrayList<>());
        setTransferZone(new WarehouseTransferZone());
        setEmployees(new HashSet<>());
    }

    @JoinColumn(name = "internal_route_id")
    @OneToOne(orphanRemoval = true)
    private Route internalRoute;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "external_route_id")
    private List<Route> externalRoutes;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "transfer_zone_id")
    private WarehouseTransferZone transferZone;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id")
    private Set<User> employees;


    public Warehouse addExternalRoute(Route externalRoute) {
        getExternalRoutes().add(externalRoute);
        return this;
    }
}
