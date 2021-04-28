package es.uniovi.paquetor.entities;

import javax.persistence.*;

@Table(name = "PARADA")
@Entity
public class Parada {
    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}