package com.example.Backend.Controller;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.AddressDTO;
import com.example.Backend.Service.Implementation.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/addresses")
@CrossOrigin(origins = "*")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addAddress")
    public ResponseEntity<?> addAddress(@RequestBody AddressDTO addressDTO) {
        addressService.addAddress(addressDTO);
        URI uri = URI.create("/addresses/" + addressDTO.getId());
        return ResponseEntity.created(uri).body(addressDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allAddresses")
    public Set<AddressDTO> allAddresses() { return addressService.allAddreses(); }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteAddress/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) throws NotFoundException {
        addressService.deleteAddress(id);
        return ResponseEntity.ok("ID " + id + " was deleted.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateAddress")
    public ResponseEntity<?> updateAddress(@RequestBody AddressDTO addressDTO) {
        addressService.updateAddress(addressDTO);
        return ResponseEntity.ok(addressDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/findAddress/{id}")
    public ResponseEntity<?> findAddress(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(addressService.findAddress(id));
    }
}
