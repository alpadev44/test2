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
@Table(name = "categories")
public class Category {
    @Id
    @SequenceGenerator(name = "categories_sequence", sequenceName = "categories_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "categories_sequence")
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String url;

    @OneToMany(mappedBy = "categories")
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();
}
