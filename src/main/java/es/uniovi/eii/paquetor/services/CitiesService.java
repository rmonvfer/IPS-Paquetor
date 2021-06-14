package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.locations.City;
import es.uniovi.eii.paquetor.repositories.CitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitiesService {

    @Autowired
    CitiesRepository citiesRepository;

    public void saveAll(City... cities){
        for (City city: cities) {
            citiesRepository.save(city);
        }
    }
}
