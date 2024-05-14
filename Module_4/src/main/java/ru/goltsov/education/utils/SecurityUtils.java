package ru.goltsov.education.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@UtilityClass
public class SecurityUtils {

    public static boolean isUserRoleOnly(UserDetails userDetails) {
        List<String> rolesStrList = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return rolesStrList.size() == 1 && rolesStrList.get(0).equals("ROLE_USER");
    }

}
