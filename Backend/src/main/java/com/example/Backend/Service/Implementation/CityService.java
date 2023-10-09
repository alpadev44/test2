package com.example.Backend.Service.Implementation;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.AddressDTO;
import com.example.Backend.Model.DataTransferObject.CityDTO;
import com.example.Backend.Model.Entity.Address;
import com.example.Backend.Model.Entity.City;
import com.example.Backend.Repository.IAddressRepository;
import com.example.Backend.Repository.ICityRepository;
import com.example.Backend.Service.ICityService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CityService implements ICityService {

    public static final Logger LOGGER = Logger.getLogger(CityService.class);
    @Autowired
    private ICityRepository cityRepository;

    @Autowired
    private IAddressRepository addressRepository;

    @Override
    public void addCity(CityDTO cityDTO) {
        City city = changeCityDTOToCity(cityDTO);

        if(city.getAddress() != null &&
           city.getAddress().getId() != null) {
            Optional<Address> searchAddress = addressRepository.findById(city.getAddress().getId());

            if(searchAddress.isPresent()) {
                Address managedAddress = searchAddress.get();

                city.setAddress(managedAddress);
                city.getAddress().setCalle(managedAddress.getCalle());
                city.getAddress().setNumero(managedAddress.getNumero());
                city.getAddress().setLocalidad(managedAddress.getLocalidad());
                city.getAddress().setProvincia(managedAddress.getProvincia());

            }
        }
        cityRepository.save(city);
    }

    @Override
    public Set<CityDTO> allCities() {
        List<City> cityList = cityRepository.findAll();
        Set<CityDTO> cityDTOSet = new HashSet<>();

        for (City citySets: cityList) {
            cityDTOSet.add(changeCityToCityDTO(citySets));
        }

        return cityDTOSet;
    }

    @Override
    public void updateCity(CityDTO cityDTO) {
        //changeCityToCityDTO(cityRepository.save(changeCityDTOToCity(cityDTO)));
        City city = changeCityDTOToCity(cityDTO);
        if(city.getId() != null) {
            Optional<City> existingCity = cityRepository.findById(city.getId());
            if(existingCity.isPresent()) {
                City managedCity = existingCity.get();

                managedCity.setName(city.getName());
                managedCity.setCountry(city.getCountry());

                if(city.getAddress() != null &&
                   city.getAddress().getId() != null) {

                    Optional<Address> searchAddress = addressRepository.findById(city.getAddress().getId());
                    if(searchAddress.isPresent()) {
                        Address managedAddress = searchAddress.get();
                        city.setAddress(managedAddress);
                    }
                }
            }
            cityRepository.save(city);
        }
    }

    @Override
    public void deleteCity(Long id) throws NotFoundException {
        Optional<City> searchCity = cityRepository.findById(id);
        if(searchCity.isPresent()) {
            cityRepository.deleteById(id);
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }
    }

    @Override
    public Optional<CityDTO> findCity(Long id) {
        Optional<City> searchCity = cityRepository.findById(id);
        if(searchCity.isEmpty()) {
            LOGGER.info("City found with id " + id);
            return Optional.empty();
        }
        return Optional.of(changeCityToCityDTO(searchCity.get()));
    }

    @Override
    public City changeCityDTOToCity(CityDTO cityDTO) {
        City city = new City();
        Address address = new Address();

        address.setId(cityDTO.getAddress_id());
        city.setAddress(address);

        city.setId(cityDTO.getId());
        city.setName(cityDTO.getName());
        city.setCountry(cityDTO.getCountry());



        return city;
    }

    @Override
    public CityDTO changeCityToCityDTO(City city) {
        CityDTO cityDTO = new CityDTO();

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(city.getAddress().getId());
        addressDTO.setCalle(city.getAddress().getCalle());
        addressDTO.setNumero(city.getAddress().getNumero());
        addressDTO.setLocalidad(city.getAddress().getLocalidad());
        addressDTO.setProvincia(city.getAddress().getProvincia());

        cityDTO.setAddressDTO(addressDTO);

        cityDTO.setId(city.getId());
        cityDTO.setName(city.getName());
        cityDTO.setCountry(city.getCountry());

        return cityDTO;
    }
}
