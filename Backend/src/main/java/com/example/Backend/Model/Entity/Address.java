package com.example.Backend.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "adresses")
public class Address {
    @Id
    @SequenceGenerator(name = "adresses_sequence", sequenceName = "adresses_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "adresses_sequence")
    private Long id;

    @Column
    private String calle;

    @Column
    private String numero;

    @Column
    private String localidad;

    @Column
    private String provincia;

}
