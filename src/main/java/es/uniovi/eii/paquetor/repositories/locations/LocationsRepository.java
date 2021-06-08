package es.uniovi.eii.paquetor.repositories.locations;

import es.uniovi.eii.paquetor.entities.locations.Location;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.users.BaseUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface LocationsRepository<T extends Location> extends CrudRepository<T, UUID> {

}