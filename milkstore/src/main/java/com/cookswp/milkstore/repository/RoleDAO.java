package com.cookswp.milkstore.repository;

import com.cookswp.milkstore.dtos.RoleDTO;

import java.util.List;

public interface RoleDAO {
    List<RoleDTO> retrieveRoleList();
}
