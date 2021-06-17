package es.uniovi.eii.paquetor.entities.locations.transferZone;

import es.uniovi.eii.paquetor.entities.locations.City;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity @Data @Accessors(chain = true)
public class WarehouseTransferzoneSection {

    public WarehouseTransferzoneSection() {
        setParcels(new ArrayList<>());
    }

    public WarehouseTransferzoneSection(City city, List<Parcel> parcels) {
        setCity(city);
        setParcels(parcels);
    }

    public WarehouseTransferzoneSection(City city, Parcel... parcels) {
        setCity(city);
        setParcels(Arrays.asList(parcels));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_transferzone_section_id")
    private List<Parcel> parcels;
}