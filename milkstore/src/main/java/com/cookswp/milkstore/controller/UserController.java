package com.cookswp.milkstore.controller;

import com.cookswp.milkstore.model.Role;
import com.cookswp.milkstore.model.UserDTO;
import com.cookswp.milkstore.service.RoleService;
import com.cookswp.milkstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final RoleService roleService; //TODO Delete this field

    @Autowired
    public UserController(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/{username}")
    public UserDTO getUser(@PathVariable String username){
        return userService.getUser(username);
    }

    //testing purpose   TODO: Delete this method
    @GetMapping("/roles")
    public List<Role> getRoles(){
        return roleService.getRoles();
    }
}
