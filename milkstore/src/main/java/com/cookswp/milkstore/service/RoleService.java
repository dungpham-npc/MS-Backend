package com.cookswp.milkstore.service;

import com.cookswp.milkstore.pojo.entities.Role;
import com.cookswp.milkstore.repository.RoleRepository.RoleRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;


    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Role getRoleByRoleName(String roleName){
        return roleRepository.findByRoleName(roleName);
    }
}
