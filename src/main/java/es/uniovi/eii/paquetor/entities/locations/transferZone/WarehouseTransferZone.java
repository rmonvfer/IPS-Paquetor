package es.uniovi.eii.paquetor.entities.locations.transferZone;

import es.uniovi.eii.paquetor.entities.locations.City;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Data @Accessors(chain = true)
public class WarehouseTransferZone {

    public WarehouseTransferZone() {
        setWarehouseTransferzoneSections(new ArrayList<>());
    }

    /**
     * Busca una sección dentro de una zona de transferencia identificada
     * por la ciudad a la que hace referencia
     * @param city Ciudad que identifica la sección dentro de la zona de transferencia
     * @return La sección de la zona de transferencia si se encuentra o null en caso contrario.
     */
    public WarehouseTransferzoneSection findTransferzoneSectionByCity (City city) {
        for (WarehouseTransferzoneSection transferzoneSection : warehouseTransferzoneSections ) {
            if (transferzoneSection.getCity().getName().equalsIgnoreCase(city.getName())) {
                return transferzoneSection;
            }
        }
        return null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "warehouse_transfer_zone_id")
    private List<WarehouseTransferzoneSection> warehouseTransferzoneSections;
}
