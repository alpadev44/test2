package com.example.Backend.Model.DataTransferObject;

import com.example.Backend.Model.Entity.User;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoleDTO {
    private Long id;
    private String name;
}
