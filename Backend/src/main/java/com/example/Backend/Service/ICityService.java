package com.example.Backend.Service;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.CityDTO;
import com.example.Backend.Model.Entity.City;

import java.util.Optional;
import java.util.Set;

public interface ICityService {
    void addCity(CityDTO cityDTO);
    Set<CityDTO> allCities();
    void updateCity(CityDTO cityDTO);
    void deleteCity(Long id) throws NotFoundException;
    Optional<CityDTO> findCity(Long id) throws NotFoundException;
    City changeCityDTOToCity(CityDTO cityDTO);
    CityDTO  changeCityToCityDTO(City city);
}
