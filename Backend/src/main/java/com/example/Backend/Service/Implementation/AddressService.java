package com.example.Backend.Service.Implementation;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.AddressDTO;
import com.example.Backend.Model.Entity.Address;
import com.example.Backend.Repository.IAddressRepository;
import com.example.Backend.Service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private IAddressRepository addressRepository;

    @Override
    public void addAddress(AddressDTO addressDTO) {
        Address address = changeAddressDTOToAddress(addressDTO);
        addressRepository.save(address);
    }

    @Override
    public Set<AddressDTO> allAddreses() {
        List<Address> addressList = addressRepository.findAll();
        Set<AddressDTO> addressDTOSet = new HashSet<>();

        for (Address addressSets : addressList) {
            addressDTOSet.add(changeAddressToAddressDTO(addressSets));
        }

        return addressDTOSet;
    }

    @Override
    public void updateAddress(AddressDTO addressDTO) {
        changeAddressToAddressDTO(addressRepository.save(changeAddressDTOToAddress(addressDTO)));
        /*
        Address address = changeAddressDTOToAddress(addressDTO);
        if(address.getId() != null) {

            Optional<Address> existingAddress = addressRepository.findById(address.getId());
            if(existingAddress.isPresent()) {
                Address managedAddress = existingAddress.get();

                managedAddress.setCalle(address.getCalle());
                managedAddress.setNumero(address.getNumero());
                managedAddress.setLocalidad(address.getLocalidad());
                managedAddress.setProvincia(address.getProvincia());
            }
            addressRepository.save(address);
        }
         */
    }

    @Override
    public void deleteAddress(Long id) throws NotFoundException {
        Optional<Address> searchAddress = addressRepository.findById(id);
        if(searchAddress.isPresent()) {
            addressRepository.deleteById(id);
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }
    }

    @Override
    public Optional<AddressDTO> findAddress(Long id) throws NotFoundException {
        Optional<Address> searchAddress = addressRepository.findById(id);
        if(searchAddress.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(changeAddressToAddressDTO(searchAddress.get()));
    }

    @Override
    public Address changeAddressDTOToAddress(AddressDTO addressDTO) {
        Address address = new Address();

        address.setId(addressDTO.getId());
        address.setCalle(addressDTO.getCalle());
        address.setNumero(addressDTO.getNumero());
        address.setLocalidad(addressDTO.getLocalidad());
        address.setProvincia(addressDTO.getProvincia());

        return address;
    }

    @Override
    public AddressDTO changeAddressToAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId(address.getId());
        addressDTO.setCalle(address.getCalle());
        addressDTO.setNumero(address.getNumero());
        addressDTO.setLocalidad(address.getLocalidad());
        addressDTO.setProvincia(address.getProvincia());

        return addressDTO;
    }
}
