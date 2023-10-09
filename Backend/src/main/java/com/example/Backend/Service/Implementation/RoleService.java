package com.example.Backend.Service.Implementation;


import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.RoleDTO;
import com.example.Backend.Model.Entity.Role;
import com.example.Backend.Model.Entity.User;
import com.example.Backend.Repository.IRoleRepository;
import com.example.Backend.Service.IRoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class RoleService implements IRoleService {
    @Autowired
    IRoleRepository iRoleRepository;

    @Override
    public void addRole(RoleDTO roleDTO) {
        Role role = changeRoleDTOToRole(roleDTO);
        iRoleRepository.save(role);
    }

    @Override
    public List<RoleDTO> allRoles() {
        List<Role> roleList = iRoleRepository.findAll();
        List<RoleDTO> roleDTOList = new ArrayList<>();

        for (Role roleLists : roleList) {
            roleDTOList.add(changeRoleToRoleDTO(roleLists));
        }

        return roleDTOList;
    }

    @Override
    public void updateRole(RoleDTO roleDTO) {

        changeRoleToRoleDTO(iRoleRepository.save(changeRoleDTOToRole(roleDTO)));
    }

    @Override
    public void deleteRole(Long id) throws NotFoundException {
        Optional<Role> searchRole = iRoleRepository.findById(id);
        if(searchRole.isPresent()) {
            iRoleRepository.deleteById(id);
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }
    }

    @Override
    public Optional<RoleDTO> findRole(Long id) throws NotFoundException {
        Optional<Role> searchRole = iRoleRepository.findById(id);
        if(searchRole.isPresent()) {
            return Optional.of(changeRoleToRoleDTO(searchRole.get()));
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }
    }

    @Override
    public Role changeRoleDTOToRole(RoleDTO roleDTO) {
        Role role = new Role();

        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());

        return role;
    }

    @Override
    public RoleDTO changeRoleToRoleDTO(Role role) {
        RoleDTO  roleDTO = new RoleDTO();

        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());

        return roleDTO;
    }
}
