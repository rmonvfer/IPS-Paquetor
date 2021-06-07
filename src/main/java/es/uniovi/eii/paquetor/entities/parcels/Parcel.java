package es.uniovi.eii.paquetor.entities.parcels;

import es.uniovi.eii.paquetor.entities.users.CustomerUser;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Accessors(chain = true)
public class Parcel {

    @Id
    @GeneratedValue
    private UUID id;

    public Parcel() { /**/ }

    public Parcel(CustomerUser sender, CustomerUser recipient) {
        setSender(sender);
        setRecipient(recipient);
    }

    @Column(name = "HEIGHT", nullable = false)
    private Double height;

    @Column(name = "WIDTH", nullable = false)
    private Double width;

    @Column(name = "DEPTH", nullable = false)
    private Double depth;

    @Column(name = "WEIGHT")
    private String weight;

    @JoinColumn(name = "SENDER_ID", nullable = false)
    @OneToOne(optional = false, orphanRemoval = true)
    private CustomerUser sender;

    @JoinColumn(name = "RECIPIENT_ID", nullable = false)
    @OneToOne(optional = false, orphanRemoval = true)
    private CustomerUser recipient;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private ParcelStatus status;

    /**
     * Indica si un paquete debe entregarse mediante una ruta interna
     * o una ruta externa (según la ubicación del emisor y el receptor)
     * @return true si ambos viven en la misma ciudad, false en caso contrario
     */
    public boolean isForInternalDispatch() {
        String senderCity = sender.getLocation().getCiudad();
        String recipientCity = recipient.getLocation().getCiudad();
        return senderCity.equalsIgnoreCase(recipientCity);
    }
}
