package com.cookswp.milkstore.repository;

import com.cookswp.milkstore.dtos.RoleDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO{

    private EntityManager entityManager;

    @Autowired
    public RoleDAOImpl(EntityManager entityManager){ this.entityManager = entityManager; }

    @Override
    public List<RoleDTO> retrieveRoleList() {
        TypedQuery<RoleDTO> queryList = entityManager.createQuery("FROM RoleDTO", RoleDTO.class);
        return queryList.getResultList();
    }
}
