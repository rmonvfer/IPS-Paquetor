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
    private List<Paquete> paquetes_enviados;

    @OneToMany(mappedBy = "receptor", orphanRemoval = true)
    private List<Paquete> paquetes_recibidos;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @JoinColumn(name = "DOMICILIO_ID")
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Ubicacion domicilio;

    @Column(name = "NOMBRE")
    private String nombre;

    @Transient
    private String tempPassword;

    @Column(name = "ROLE")
    private String role;

    @JoinColumn(name = "ALMACEN_ID")
    @ManyToOne(optional = true)
    private Almacen almacen;

    @OneToOne(mappedBy = "conductor", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Transporte transporte;

    public Transporte getTransporte() {
        return transporte;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Usuario(){ }

    public Usuario(String email, String password, String nombre, Ubicacion domicilio){
        setEmail(email);
        setPassword(password);
        setNombre(nombre);
        setDomicilio(domicilio);
        setTempPassword(password);
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

    public List<Paquete> getPaquetes_enviados() {
        return paquetes_enviados;
    }

    public void setPaquetes_enviados(List<Paquete> paquetes) {
        this.paquetes_enviados = paquetes;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", domicilio=" + domicilio +
                ", nombre='" + nombre + '\'' +
                ", tempPassword='" + tempPassword + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

