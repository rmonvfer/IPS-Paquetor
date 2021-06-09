package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.locations.Location;
import es.uniovi.eii.paquetor.repositories.LocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationsService {

    @Autowired
    LocationsRepository locationsRepository;

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();
        locationsRepository.findAll().forEach(locations::add);
        return locations;
    }

    public Location getLocation(Long id) {
        return locationsRepository.findById(id).get();
    }

    public void addLocation(Location location) {
        locationsRepository.save(location);
    }

    public void deleteLocation(Long id) {
        locationsRepository.deleteById(id);
    }
}
