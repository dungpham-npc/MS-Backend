package com.cookswp.milkstore.repositories.UserRepository;

import com.cookswp.milkstore.pojo.dtos.UserModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmailAddress(String emailAddress);
    User findByPhoneNumber(String phoneNumber);
    @Query("SELECT u FROM User u WHERE u.visibilityStatus = true AND u.role.roleId IN (2, 3, 4, 5)")
    List<User> findAllStaffs();
    @Query("SELECT u FROM User u WHERE u.visibilityStatus = true AND u.role.roleId = 1")
    List<User> findAllMembers();
    @Modifying  // Required for update queries (modifying database)
    @Query("UPDATE User u SET u.emailAddress = :email, u.phoneNumber = :phone, u.password = :password, u.username = :username WHERE u.userId = :id")
    void updateUser(@Param("id") int id, @Param("email") String email,
                    @Param("phone") String phone, @Param("password") String password,
                    @Param("username") String username);
    @Modifying
    @Query("UPDATE User u SET u.visibilityStatus = false WHERE u.userId = :id")
    void deleteUser(@Param("id") int id);
}
