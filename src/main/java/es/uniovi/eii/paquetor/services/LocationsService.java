package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.locations.Home;
import es.uniovi.eii.paquetor.entities.locations.Location;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.repositories.locations.HomesRepository;
import es.uniovi.eii.paquetor.repositories.locations.WarehousesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocationsService {

    @Autowired
    WarehousesRepository warehousesRepository;

    @Autowired
    HomesRepository homesRepository;

    public List<Warehouse> getWarehouses() {
        List<Warehouse> warehouses = new ArrayList<>();
        warehousesRepository.findAll().forEach(warehouses::add);
        return warehouses;
    }

    public List<Home> getHomes() {
        List<Home> homes = new ArrayList<>();
        homesRepository.findAll().forEach(homes::add);
        return homes;
    }

    public Warehouse getWarehouse(UUID id) {
        return warehousesRepository.findById(id).get();
    }

    public Home getHome(UUID id) {
        return homesRepository.findById(id).get();
    }

    public void addWarehouse(Warehouse warehouse) {
        warehousesRepository.save(warehouse);
    }

    public void addHome(Home home) {
        homesRepository.save(home);
    }

    public void deleteWarehouse(UUID id) {
        warehousesRepository.deleteById(id);
    }

    public void deleteHome(UUID id) {
        homesRepository.deleteById(id);
    }
}
