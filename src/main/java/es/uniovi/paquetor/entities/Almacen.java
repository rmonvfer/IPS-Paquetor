package es.uniovi.paquetor.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Table(name = "ALMACEN")
@Entity
public class Almacen {
    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "UBICACION_ID")
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Ubicacion ubicacion;

    @OneToMany(mappedBy = "almacen", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Usuario> empleados;

    @OneToOne(mappedBy = "almacen_interno_origen", cascade = CascadeType.PERSIST, optional = false, orphanRemoval = true)
    private RutaInterna rutaInterna;

    @OneToOne(mappedBy = "almacen_origen_externo", cascade = CascadeType.PERSIST, optional = false, orphanRemoval = true)
    private RutaExterna rutaExterna;

    public RutaExterna getRutaExterna() {
        return rutaExterna;
    }

    public void setRutaExterna(RutaExterna rutaExterna) {
        this.rutaExterna = rutaExterna;
    }

    public RutaInterna getRutaInterna() {
        return rutaInterna;
    }

    public void setRutaInterna(RutaInterna rutaInterna) {
        this.rutaInterna = rutaInterna;
    }

    public List<Usuario> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Usuario> empleados) {
        this.empleados = empleados;
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