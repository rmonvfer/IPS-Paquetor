package es.uniovi.eii.paquetor.entities.parcels;

import javax.persistence.Entity;

@Entity
public class InPickupParcel extends Parcel {

    @Override
    ParcelStatus getStatus() {
        return ParcelStatus.IN_PICKUP;
    }
}
