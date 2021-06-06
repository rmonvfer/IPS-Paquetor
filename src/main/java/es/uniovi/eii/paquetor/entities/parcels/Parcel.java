package es.uniovi.eii.paquetor.entities.parcels;

import es.uniovi.eii.paquetor.entities.users.CustomerUser;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Parcel {

    @Id
    @GeneratedValue
    private UUID id;

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

    abstract ParcelStatus getStatus();
}
