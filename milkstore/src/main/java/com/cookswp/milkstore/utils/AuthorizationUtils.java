package com.cookswp.milkstore.utils;

import com.cookswp.milkstore.exception.UnauthorizedAccessException;
import com.cookswp.milkstore.pojo.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizationUtils {

    public static void checkAuthorization(String requiredRole) throws UnauthorizedAccessException {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!currentUser.getRole().getRoleName().equals(requiredRole)) {
            throw new UnauthorizedAccessException();
        }
    }
}
