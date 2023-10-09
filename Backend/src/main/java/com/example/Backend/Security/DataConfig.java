package com.example.Backend.Security;

import com.example.Backend.Model.Entity.Role;
import com.example.Backend.Model.Entity.User;
import com.example.Backend.Repository.IRoleRepository;
import com.example.Backend.Repository.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class DataConfig  implements ApplicationRunner {

    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IRoleRepository iRoleRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        BCryptPasswordEncoder cifrador= new BCryptPasswordEncoder();
        String passACifrar="1234567";
        String passCifrada=cifrador.encode(passACifrar);

        Role role = new Role();
        role.setName("ADMIN");
        Role saveRole = iRoleRepository.save(role);
        Role managedRole = new Role();
        managedRole.setId(saveRole.getId());

        User user= new User();
        user.setName("Alejandro");
        user.setLastName("Padron");
        user.setEmail("ale@mail.com");
        user.setPassword(passCifrada);
        user.setRoles(saveRole);

        iUserRepository.save(user);
    }
}



