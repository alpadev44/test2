package com.example.Backend.Model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "products")
public class Product {
    @Id
    @SequenceGenerator(name = "products_sequence", sequenceName = "products_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "products_sequence")
    private Long id;

    @Column
    private String title;

    @Column
    private Double price;

    @Column
    private String description;

    @Column
    private LocalDateTime dateTimeProduct;

    @Min(1)
    @Max(5)
    @Column
    private Integer Score;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "images_id", referencedColumnName = "id")
    private Set<Image> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categories_id", referencedColumnName = "id", nullable = false)
    private Category categories;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cities_id", referencedColumnName = "id")
    private City city;

    @JsonIgnore
    @OneToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private Set<Booking> bookings = new HashSet<>();

}
