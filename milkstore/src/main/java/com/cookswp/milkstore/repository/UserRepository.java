package com.cookswp.milkstore.repository;

import com.cookswp.milkstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmailAddress(String emailAddress);
    @Query("SELECT u FROM User u WHERE u.role.roleId IN (2, 3, 4, 5)")
    List<User> findAllStaffs();
    @Query("SELECT u FROM User u WHERE u.role.roleId = 1")
    List<User> findAllMembers();
}
