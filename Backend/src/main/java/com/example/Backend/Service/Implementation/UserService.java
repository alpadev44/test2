package com.example.Backend.Service.Implementation;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.*;
import com.example.Backend.Model.Entity.*;
import com.example.Backend.Repository.IBookingRepository;
import com.example.Backend.Repository.IProductRepository;
import com.example.Backend.Repository.IRoleRepository;
import com.example.Backend.Repository.IUserRepository;
import com.example.Backend.Service.IUserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@AllArgsConstructor
@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IRoleRepository iRoleRepository;
    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private IBookingRepository iBookingRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public void addUser(UserDTO userDTO) {

        User user = changeUserDTOToUser(userDTO);

       if(user.getRoles() != null && user.getRoles().getId() != null) {
            Optional<Role> searchRole = iRoleRepository.findById(user.getRoles().getId());

            if(searchRole.isPresent()) {
                Role managedRole = searchRole.get();

                user.setRoles(managedRole);
                user.getRoles().setName(managedRole.getName());
            }
       }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        iUserRepository.save(user);

        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Email Confirmation</title>\n"
                + "    <style>\n"
                + "        body {\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            display: flex;\n"
                + "            justify-content: center;\n"
                + "            align-items: center;\n"
                + "            height: 100vh;\n"
                + "            flex-direction: column;\n"
                + "        }\n"
                + "        h1 {\n"
                + "            color: black;\n"
                + "            width: 40vw;\n"
                + "            height: 5vw;\n"
                + "        }\n"
                + "        p {\n"
                + "            color: black;\n"
                + "            width: 40vw;\n"
                + "            height: 3vw;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <h1>Bienvenido a Sushi Ttor !!!</h1>\n"
                + "    <p>Gracias por confirmar este Email, su cuenta ha sido verificada.</p>\n"
                + "</body>\n"
                + "</html>";

        emailService.enviarCorreo(user.getEmail(), "Bienvenido", html, true);
        //emailService.enviarCorreo(user.getEmail(), "Bienvenido", "¡Hola " + user.getEmail() + "! Gracias por registrarte.");
    }

    @Override
    public List<UserDTO> allUsers() {
        List<User> userList = iUserRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User userLists : userList) {
            userDTOList.add(changeUserToUserDTO(userLists));
        }

        return userDTOList;
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        Optional<Role> searchRole = iRoleRepository.findById(userDTO.getRoles_id());
        if(searchRole.isPresent()) {
            changeUserToUserDTO(iUserRepository.save(changeUserDTOToUser(userDTO)));
        }
    }

    @Override
    public void deleteUser(Long id) throws NotFoundException {
        Optional<User> searchUser = iUserRepository.findById(id);
        if(searchUser.isPresent()) {
            iUserRepository.deleteById(id);
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }
    }

    @Override
    public Optional<UserDTO> findUser(Long id) throws NotFoundException {
        Optional<User> userSearch = iUserRepository.findById(id);
        if(userSearch.isPresent()) {
            return Optional.of(changeUserToUserDTO(userSearch.get()));
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }
    }

    @Override
    public User changeUserDTOToUser(UserDTO userDTO) {
        User user = new User();
        Role role = new Role();


        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        role.setId(userDTO.getRoles_id());
        role.setName(userDTO.getName());

        user.setRoles(role);

        return user;
    }

    @Override
    public UserDTO changeUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        Set<ProductDTO> productDTOSet = new HashSet<>();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoles_id(user.getRoles().getId());

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(user.getRoles().getId());
        roleDTO.setName(user.getRoles().getName());

        userDTO.setRoleDTO(roleDTO);

        if(user.getFavoriteProduct() != null) {
            for (Product productSet : user.getFavoriteProduct()  ) {
                productDTOSet.add(productService.changeProductToProductDTO(productSet));
            }
            userDTO.setProductDTO(productDTOSet);
        }

        return userDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> searchUser = iUserRepository.findByEmail(username);

        if(searchUser.isPresent()) {
            return (UserDetails) searchUser.get();
        }
        else{
            throw new UsernameNotFoundException("Error. Usuario con email "+username+" no encontrado en la BD");
        }

    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) throws NotFoundException {
        Optional<User> userSearch = iUserRepository.findByEmail(email);
        if (userSearch.isPresent()) {
            return Optional.of(changeUserToUserDTO(userSearch.get()));
        } else {
            throw new NotFoundException("User with email " + email + " not found");
        }
    }

    public void getBookingSet(BookingDTO bookingDTO) {

        Optional<Booking> searchBooking = iBookingRepository.findById(bookingDTO.getId());
        Optional<User> searchUser = iUserRepository.findById(bookingDTO.getUsers_id());

        if(searchBooking.isPresent()  && searchUser.isPresent()) {
            Booking managedBooking = searchBooking.get();
            User managedUser = searchUser.get();

            managedUser.getBookings().add(managedBooking);
            iUserRepository.save(managedUser);
        }
    }

    /*
    public void generateBooking(Long userId,Long productId) {

        Optional<User> user = iUserRepository.findById(userId);
        Optional<Product> product = iProductRepository.findById(productId);

        if (user.isPresent() && product.isPresent()) {
            Booking booking = new Booking();

            booking.setUsers(user.get());
            booking.setProducts(product.get());

            iBookingRepository.save(booking);
        }
    }

     */

}
