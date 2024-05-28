package com.cookswp.milkstore.service;

import com.cookswp.milkstore.model.Role;
import com.cookswp.milkstore.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Role getRoleByRoleName(String roleName){
        return roleRepository.findByRoleName(roleName);
    }
}
