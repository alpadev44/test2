package com.example.Backend.Service;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.UserDTO;
import com.example.Backend.Model.Entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserService {
    void addUser(UserDTO userDTO);
    List<UserDTO> allUsers();
    void updateUser(UserDTO userDTO);
    void deleteUser(Long id) throws NotFoundException;
    Optional<UserDTO> findUser(Long id) throws NotFoundException;
    User changeUserDTOToUser(UserDTO userDTO);
    UserDTO changeUserToUserDTO(User user);
    Optional<UserDTO> getUserByEmail(String email) throws NotFoundException;

}
