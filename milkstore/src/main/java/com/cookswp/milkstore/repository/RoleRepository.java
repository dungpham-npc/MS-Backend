package com.cookswp.milkstore.repository;

import com.cookswp.milkstore.pojo.dtos.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}
