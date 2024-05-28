package com.cookswp.milkstore.repositories.RoleRepository;

import com.cookswp.milkstore.pojo.dtos.UserModel.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}
