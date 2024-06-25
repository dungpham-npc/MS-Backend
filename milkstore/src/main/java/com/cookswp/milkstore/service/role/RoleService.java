package com.cookswp.milkstore.service.role;

import com.cookswp.milkstore.pojo.entities.Role;
import com.cookswp.milkstore.repository.role.RoleRepository;
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
