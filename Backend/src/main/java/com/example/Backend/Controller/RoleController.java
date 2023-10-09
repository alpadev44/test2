package com.example.Backend.Controller;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.RoleDTO;
import com.example.Backend.Model.Entity.Role;
import com.example.Backend.Service.Implementation.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addRole")
    public ResponseEntity<?> addRole(@RequestBody RoleDTO roleDTO) {
        roleService.addRole(roleDTO);
        URI uri = URI.create("/roles/" + roleDTO.getId());
        return ResponseEntity.created(uri).body(roleDTO);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allRoles")
    public List<RoleDTO> allRoles() { return roleService.allRoles(); }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) throws NotFoundException {
        roleService.deleteRole(id);
        return ResponseEntity.ok("ID " + id + " was deleted.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateRole")
    public ResponseEntity<?> updateRole(@RequestBody RoleDTO roleDTO) {
        roleService.updateRole(roleDTO);
        return ResponseEntity.ok(roleDTO);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/findRole/{id}")
    public ResponseEntity<?> findRole(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(roleService.findRole(id));
    }
}
