package es.uniovi.eii.paquetor.entities.parcels;

import javax.persistence.Entity;

@Entity
public class InOriginParcel extends Parcel {

    @Override
    ParcelStatus getStatus() {
        return ParcelStatus.IN_ORIGIN;
    }
}
