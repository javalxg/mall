package com.wondersgroup.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2012:28
 */
public class UmsAdminTest {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("12345678"));
        System.out.println(passwordEncoder.matches("12345678","$2a$10$ZPRA9SjSBgtUxnZgugEEhea/94wLaDREYYxX1oYkl/OETsOJUlOPq"));
    }
}
