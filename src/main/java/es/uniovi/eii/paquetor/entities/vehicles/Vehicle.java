package es.uniovi.eii.paquetor.entities.vehicles;

import lombok.Data;

import javax.persistence.*;

@Entity @Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}