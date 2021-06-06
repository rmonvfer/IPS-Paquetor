package es.uniovi.eii.paquetor.entities.parcels;

import javax.persistence.Entity;

@Entity
public class InDeliveryParcel extends Parcel {

    @Override
    ParcelStatus getStatus() {
        return ParcelStatus.IN_DELIVERY;
    }
}
