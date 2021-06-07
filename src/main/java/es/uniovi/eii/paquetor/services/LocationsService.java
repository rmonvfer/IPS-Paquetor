package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.locations.Location;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import es.uniovi.eii.paquetor.entities.users.CustomerUser;
import es.uniovi.eii.paquetor.repositories.LocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocationsService {

    @Autowired
    LocationsRepository locationsRepository;

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();
        locationsRepository.findAll().forEach(locations::add);
        return locations;
    }

    public Location getLocation(UUID id) {
        return locationsRepository.findById(id).get();
    }

    public void addLocation(Location location) {
        locationsRepository.save(location);
    }

    public void deleteLocation(UUID id) {
        locationsRepository.deleteById(id);
    }
}
