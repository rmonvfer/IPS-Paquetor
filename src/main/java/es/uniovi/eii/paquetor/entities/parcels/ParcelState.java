package es.uniovi.eii.paquetor.entities.parcels;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Entity @Data
@Accessors(chain = true)
public class ParcelState {

    @Id
    @Type(type = "uuid-char")
    private UUID id;

    public ParcelState() { /**/ }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ParcelStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updated_date;

    @Column(name = "internal_index")
    private int internal_index;

    public String getNiceStatus() {
        HashMap<ParcelStatus, String> statusStringHashMap = new HashMap<>();
        statusStringHashMap.put(ParcelStatus.NOT_PROCESSED, "Paquete registrado en el sistema, pendiente de procesamiento");
        statusStringHashMap.put(ParcelStatus.PICKUP_READY, "Pendiente de recogida por el transportista");
        statusStringHashMap.put(ParcelStatus.PICKED_UP, "Recogido");
        statusStringHashMap.put(ParcelStatus.IN_DELIVERY, "En reparto");
        statusStringHashMap.put(ParcelStatus.IN_DESTINY, "En almacén de destino");
        statusStringHashMap.put(ParcelStatus.IN_ORIGIN, "En almacén de origen");
        statusStringHashMap.put(ParcelStatus.IN_PICKUP, "El transportista ha salido para recoger el paquete");
        statusStringHashMap.put(ParcelStatus.IN_TRANSFER_ZONE, "En preparación para la transferencia al almacén de destino");
        statusStringHashMap.put(ParcelStatus.IN_LONG_DISTANCE_TRANSPORT, "En transporte de larga distancia");
        statusStringHashMap.put(ParcelStatus.DELIVERED, "Entregado");
        return statusStringHashMap.get(getStatus());
    }

    public boolean hasBeenDelivered(){
        return getStatus() == ParcelStatus.DELIVERED;
    }
}