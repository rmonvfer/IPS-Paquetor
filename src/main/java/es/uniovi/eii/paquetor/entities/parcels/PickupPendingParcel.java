package es.uniovi.eii.paquetor.entities.parcels;

import javax.persistence.Entity;

@Entity
public class PickupPendingParcel extends Parcel {

    @Override
    ParcelStatus getStatus() {
        return ParcelStatus.PICKUP_PENDING;
    }
}
