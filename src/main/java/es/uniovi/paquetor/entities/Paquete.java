package es.uniovi.paquetor.entities;

import javax.persistence.*;

@Table(name = "PAQUETE")
@Entity
public class Paquete {
    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "EMISOR_ID_USUARIO", unique = true)
    @ManyToOne
    private Usuario emisor;

    @JoinColumn(name = "RECEPTOR_ID_USUARIO", unique = true)
    @ManyToOne
    private Usuario receptor;

    @Column(name = "PESO", nullable = false)
    private Double peso;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false)
    private EnumType estado;

    public EnumType getEstado() {
        return estado;
    }

    public void setEstado(EnumType estado) {
        this.estado = estado;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}