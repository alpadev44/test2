package com.example.Backend.Controller;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.CityDTO;
import com.example.Backend.Service.Implementation.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/cities")
@CrossOrigin(origins = "*")
public class CityController {
    @Autowired
    private CityService cityService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addCity")
    public ResponseEntity<?> addImage(@RequestBody CityDTO cityDTO) {
        cityService.addCity(cityDTO);
        URI uri = URI.create("/cities/" + cityDTO.getId());
        return ResponseEntity.created(uri).body(cityDTO);
    }

 //   @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allCities")
    public Set<CityDTO> allCities() { return cityService.allCities(); }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteCity/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) throws NotFoundException {
        cityService.deleteCity(id);
        return ResponseEntity.ok("ID " + id + " was deleted.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateCity")
    public ResponseEntity<?> updateCity(@RequestBody CityDTO cityDTO) {
        cityService.updateCity(cityDTO);
        return ResponseEntity.ok(cityDTO);
    }

 //   @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/findCity/{id}")
    public ResponseEntity<?> findCity(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(cityService.findCity(id));
    }
}
