package es.uniovi.paquetor.entities;

import javax.persistence.*;

@Table(name = "UBICACION")
@Entity
public class Ubicacion {
    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CIUDAD")
    private String ciudad;

    @Column(name = "CALLE")
    private String calle;

    @Column(name = "NUMERO")
    private Integer numero;

    @OneToOne(mappedBy = "domicilio", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Usuario usuario;

    @OneToOne(mappedBy = "ubicacion", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Almacen almacen;

    public Ubicacion() {}

    public Ubicacion(String ciudad, String calle, Integer numero) {
        setCiudad(ciudad);
        setCalle(calle);
        setNumero(numero);
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}