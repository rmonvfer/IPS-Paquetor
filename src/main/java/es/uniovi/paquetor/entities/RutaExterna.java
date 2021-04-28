package es.uniovi.paquetor.entities;

import javax.persistence.*;
import java.util.List;

@Table(name = "RUTA_EXTERNA")
@Entity
public class RutaExterna {
    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "ALMACEN_ORIGEN_EXTERNO_ID", nullable = false)
    @OneToOne(cascade = CascadeType.PERSIST, optional = false, orphanRemoval = true)
    private Almacen almacen_origen_externo;

    @JoinColumn(name = "RUTA_EXTERNA_ID", nullable = false)
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Parada> paradas;

    @JoinColumn(name = "TRANSPORTE_ID", nullable = false)
    @OneToOne(cascade = CascadeType.PERSIST, optional = false, orphanRemoval = true)
    private Transporte transporte;

    public Transporte getTransporte() {
        return transporte;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }

    public List<Parada> getParadas() {
        return paradas;
    }

    public void setParadas(List<Parada> paradas) {
        this.paradas = paradas;
    }

    public Almacen getAlmacen_origen_externo() {
        return almacen_origen_externo;
    }

    public void setAlmacen_origen_externo(Almacen almacen_origen_externo) {
        this.almacen_origen_externo = almacen_origen_externo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}