package com.example.Backend.Service;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.AddressDTO;
import com.example.Backend.Model.Entity.Address;

import java.util.Optional;
import java.util.Set;

public interface IAddressService {
    void addAddress(AddressDTO addressDTO);
    Set<AddressDTO> allAddreses();
    void updateAddress(AddressDTO addressDTO);
    void deleteAddress(Long id) throws NotFoundException;
    Optional<AddressDTO> findAddress(Long id) throws NotFoundException;
    Address changeAddressDTOToAddress(AddressDTO addressDTO);
    AddressDTO  changeAddressToAddressDTO(Address address);
}
