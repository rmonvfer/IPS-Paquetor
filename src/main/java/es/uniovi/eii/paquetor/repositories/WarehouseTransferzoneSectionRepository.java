package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.locations.transferZone.WarehouseTransferzoneSection;
import org.springframework.data.repository.CrudRepository;

public interface WarehouseTransferzoneSectionRepository extends CrudRepository<WarehouseTransferzoneSection, Long> {
    WarehouseTransferzoneSection findByCity_NameIgnoreCase(String name);

}