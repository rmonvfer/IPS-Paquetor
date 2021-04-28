package es.uniovi.paquetor.entities;

import javax.persistence.*;
import java.util.List;

@Table(name = "USUARIO")
@Entity
public class Usuario {
    @Column(name = "ID_USUARIO", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_usuario;

    @OneToMany(mappedBy = "emisor", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Paquete> paquetes;

    @OneToMany(mappedBy = "receptor", orphanRemoval = true)
    private List<Paquete> paquetes_recibidos;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @JoinColumn(name = "DOMICILIO_ID")
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Ubicacion domicilio;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    public Usuario(){}

    public Usuario(String email, String password, String nombre, Ubicacion domicilio){
        setEmail(email);
        setPassword(password);
        setNombre(nombre);
        setDomicilio(domicilio);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ubicacion getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Ubicacion domicilio) {
        this.domicilio = domicilio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Paquete> getPaquetes_recibidos() {
        return paquetes_recibidos;
    }

    public void setPaquetes_recibidos(List<Paquete> paquetes_recibidos) {
        this.paquetes_recibidos = paquetes_recibidos;
    }

    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(List<Paquete> paquetes) {
        this.paquetes = paquetes;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }
}