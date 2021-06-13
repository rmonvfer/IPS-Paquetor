package es.uniovi.eii.paquetor.entities.parcels;

import es.uniovi.eii.paquetor.entities.User;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Accessors(chain = true)
@Log4j2
public class Parcel {

    @Id
    @Type(type = "uuid-char")
    private UUID id;

    public Parcel() { /**/ }

    public Parcel(User sender, User recipient) {
        setSender(sender);
        setRecipient(recipient);
        statesRecord = new LinkedList<>();
    }

    @Column(name = "HEIGHT", nullable = false)
    private Double height;

    @Column(name = "WIDTH", nullable = false)
    private Double width;

    @Column(name = "DEPTH", nullable = false)
    private Double depth;

    @Column(name = "WEIGHT")
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @OrderBy("internal_index ASC")
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "parcel_id")
    private List<ParcelState> statesRecord;

    /**
     * Devuelve el estado actual del paquete
     * @return estado actual
     */
    public ParcelState getCurrentState() {
        ParcelState current;
        try {
            current = statesRecord.get(statesRecord.size() -1);

        } catch (Exception exception) {
            current = null;
        }
        return current;
    }

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
