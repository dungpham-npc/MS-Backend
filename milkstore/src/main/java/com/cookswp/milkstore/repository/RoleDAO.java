package com.cookswp.milkstore.repository;

import com.cookswp.milkstore.model.RoleDTO;

import java.util.List;

public interface RoleDAO {
    List<RoleDTO> retrieveRoleList();
}
