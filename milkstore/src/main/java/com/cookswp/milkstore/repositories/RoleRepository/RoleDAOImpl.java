package com.cookswp.milkstore.repositories.RoleRepository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAOImpl implements RoleDAO{

    private EntityManager entityManager;

    @Autowired
    public RoleDAOImpl(EntityManager entityManager){ this.entityManager = entityManager; }

//    @Override
//    public List<RoleDTO> retrieveRoleList() {
//        TypedQuery<RoleDTO> queryList = entityManager.createQuery("FROM RoleDTO", RoleDTO.class);
//        return queryList.getResultList();
//    }
}
