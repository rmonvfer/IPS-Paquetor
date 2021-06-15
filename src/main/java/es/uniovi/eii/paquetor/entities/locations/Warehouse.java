package es.uniovi.eii.paquetor.entities.locations;

import es.uniovi.eii.paquetor.entities.routes.Route;
import es.uniovi.eii.paquetor.entities.locations.transferZone.WarehouseTransferZone;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    }

    @JoinColumn(name = "internal_route_id")
    @OneToOne(orphanRemoval = true)
    private Route internalRoute;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "external_route_id")
    private List<Route> externalRoutes;

    @OneToOne(cascade = CascadeType.PERSIST, optional = false, orphanRemoval = true)
    @JoinColumn(name = "transfer_zone_id", nullable = false)
    private WarehouseTransferZone transferZone;

    public Warehouse addExternalRoute(Route externalRoute) {
        getExternalRoutes().add(externalRoute);
        return this;
    }
}
