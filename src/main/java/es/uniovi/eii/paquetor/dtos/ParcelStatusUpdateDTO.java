package es.uniovi.eii.paquetor.dtos;

import es.uniovi.eii.paquetor.entities.parcels.ParcelStatus;
import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public class ParcelStatusUpdateDTO {
    ParcelStatus parcelStatus;
    String parcelUUID;
}
