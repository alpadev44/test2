package com.example.Backend.Service;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.RoleDTO;
import com.example.Backend.Model.DataTransferObject.UserDTO;
import com.example.Backend.Model.Entity.Role;
import com.example.Backend.Model.Entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IRoleService {
    void addRole(RoleDTO roleDTO );
    List<RoleDTO > allRoles();
    void updateRole(RoleDTO  roleDTO );
    void deleteRole(Long id) throws NotFoundException;
    Optional<RoleDTO> findRole(Long id) throws NotFoundException;
    Role changeRoleDTOToRole(RoleDTO roleDTO);
    RoleDTO changeRoleToRoleDTO(Role role);
}
