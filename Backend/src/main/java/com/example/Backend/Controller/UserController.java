
package com.example.Backend.Controller;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.BookingDTO;
import com.example.Backend.Model.DataTransferObject.UserDTO;
import com.example.Backend.Service.Implementation.RoleService;
import com.example.Backend.Service.Implementation.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        URI uri = URI.create("/users/" + userDTO.getId());
        return ResponseEntity.created(uri).body(userDTO);
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/allUsers")
    public List<UserDTO> allUsers() { return userService.allUsers(); }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws NotFoundException{
        userService.deleteUser(id);
        return ResponseEntity.ok("ID " + id + " Was Deleted.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
        return ResponseEntity.ok(userDTO);
    }

  //  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/findUser/{id}")
    public ResponseEntity<?> findUser(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(userService.findUser(id));
    }

    /*
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/addProductFavorite")
    public ResponseEntity<?> addProductFavorite(@RequestBody FavoriteDTO favoriteDTO) throws NotFoundException {
        userService.addProductFavorite(favoriteDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

     */

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/getBookingSet")
    public ResponseEntity<?> getBookingSet(@RequestBody BookingDTO bookingDTO) throws NotFoundException {
        userService.getBookingSet(bookingDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

//Como vamos a manejar las requeest/responses en el frontend?
//El frontend me manda DTO que pongan los usuarios, el DTO se acomoda en el backend.
//usuario usa el front, front manda al backend, backend procesa y responde, y es el ciclo (siempre recibe DTO)
// controller siempre tira DTO.
//Despues el frontend envia el JWT
//Que tipo de objeto comun van a manejar para comunicarse entre el frontend y el backend?
//Json