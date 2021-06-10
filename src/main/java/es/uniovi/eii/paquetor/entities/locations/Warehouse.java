package es.uniovi.eii.paquetor.entities.locations;

import es.uniovi.eii.paquetor.entities.Route;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
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

    @JoinColumn(name = "INTERNAL_ROUTE_ID")
    @OneToOne(orphanRemoval = true)
    private Route internalRoute;

    @JoinColumn(name = "EXTERNAL_WAREHOUSE_ID")
    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Warehouse> externalWarehouses;
}
