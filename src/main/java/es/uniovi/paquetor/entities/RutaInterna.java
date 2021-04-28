package es.uniovi.paquetor.entities;

import javax.persistence.*;
import java.util.List;

@Table(name = "RUTA")
@Entity
public class RutaInterna {
    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "ALMACEN_INTERNO_ORIGEN_ID", nullable = false)
    @OneToOne(optional = false, orphanRemoval = true)
    private Almacen almacen_interno_origen;

    @JoinColumn(name = "RUTA_INTERNA_ID", nullable = false)
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Parada> paradas;

    @JoinColumn(name = "TRANSPORTE_ID", nullable = false)
    @OneToOne(optional = false, orphanRemoval = true)
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

    public Almacen getAlmacen_interno_origen() {
        return almacen_interno_origen;
    }

    public void setAlmacen_interno_origen(Almacen almacen_interno_origen) {
        this.almacen_interno_origen = almacen_interno_origen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}