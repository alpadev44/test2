package com.example.Backend.Repository;

import com.example.Backend.Model.Entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICityRepository extends JpaRepository<City, Long> {
}
