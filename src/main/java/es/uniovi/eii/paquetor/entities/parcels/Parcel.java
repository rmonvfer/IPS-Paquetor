package es.uniovi.eii.paquetor.entities.parcels;

import es.uniovi.eii.paquetor.entities.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Accessors(chain = true)
public class Parcel {

    @Id
    @Type(type = "uuid-char")
    private UUID id;

    public Parcel() { /**/ }

    public Parcel(User sender, User recipient) {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private ParcelStatus status;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

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
