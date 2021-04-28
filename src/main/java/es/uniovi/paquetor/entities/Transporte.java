package es.uniovi.paquetor.entities;

import javax.persistence.*;

@Table(name = "TRANSPORTE")
@Entity
public class Transporte {
    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "UBICACION_ID", nullable = false)
    @OneToOne(cascade = CascadeType.PERSIST, optional = false, orphanRemoval = true)
    private Ubicacion ubicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", nullable = false)
    private EnumType tipo;

    @JoinColumn(name = "CONDUCTOR_ID")
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Empleado conductor;

    public Empleado getConductor() {
        return conductor;
    }

    public void setConductor(Empleado conductor) {
        this.conductor = conductor;
    }

    public EnumType getTipo() {
        return tipo;
    }

    public void setTipo(EnumType tipo) {
        this.tipo = tipo;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}