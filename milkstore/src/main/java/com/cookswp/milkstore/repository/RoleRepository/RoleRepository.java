package com.cookswp.milkstore.repository.RoleRepository;

import com.cookswp.milkstore.pojo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}
