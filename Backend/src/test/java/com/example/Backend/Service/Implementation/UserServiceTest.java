package com.example.Backend.Service.Implementation;

import com.example.Backend.Model.Entity.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    public void addUser() {
        User saveUser = new User();


    }

    @Test
    void allUsers() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void findUser() {
    }

    @Test
    void changeUserDTOToUser() {
    }

    @Test
    void changeUserToUserDTO() {
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void addProductFavorite() {
    }

    @Test
    void getBookingSet() {
    }
}