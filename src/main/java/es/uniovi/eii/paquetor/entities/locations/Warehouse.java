package es.uniovi.eii.paquetor.entities.locations;

import es.uniovi.eii.paquetor.entities.Route;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * Un almacén. Dispone de 3 elementos principales
 *  - Una ruta interna, cuyas paradas son ubicaciones en la propia ciudad
 *  - Una lista Almacenes, representando los destinos de las rutas externas (que salen del almacén actual).
 *  - Una zona de transferencia, donde se colocan los paquetes que llegan de la ruta externa hasta ser
 *    transferidos a la ruta interna.
 */
@Entity
@Getter @Setter
public class Warehouse extends Location {
    @JoinColumn(name = "INTERNAL_ROUTE_ID", nullable = false)
    @OneToOne(optional = false, orphanRemoval = true)
    private Route internalRoute;

    @JoinColumn(name = "EXTERNAL_WAREHOUSE_ID", nullable = false)
    @OneToMany(orphanRemoval = true)
    private List<Warehouse> externalWarehouses;
}
