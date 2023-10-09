package com.example.Backend.Model.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "bookings")
public class Booking {
    @Id
    @SequenceGenerator(name = "bookings_sequence", sequenceName = "bookings_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bookings_sequence")
    private Long id;

    @Column
    private LocalDateTime start_date;

    @Column
    private LocalDateTime finish_date;

    @ManyToOne
    @JoinColumn(name = "products_id", referencedColumnName = "id", nullable = false)
    private Product products;

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = false)
    private User users;
}
