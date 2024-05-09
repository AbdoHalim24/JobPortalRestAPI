package com.AbdoHalim.JobPortal.Service.implementaion;

import com.AbdoHalim.JobPortal.Entity.User;
import com.AbdoHalim.JobPortal.Repository.UserRepo;
import com.AbdoHalim.JobPortal.Service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
    private  UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= userRepo.findByEmail(email);
        if (user==null){
            throw  new UsernameNotFoundException("User Not Found");
        }
        return new CustomUserDetails(user) ;

    }
}
