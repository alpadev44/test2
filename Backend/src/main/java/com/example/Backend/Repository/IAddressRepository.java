package com.example.Backend.Repository;

import com.example.Backend.Model.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressRepository extends JpaRepository<Address, Long> {
}
