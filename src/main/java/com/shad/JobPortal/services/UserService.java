package com.shad.JobPortal.services;

import com.shad.JobPortal.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shad.JobPortal.repository.UserRepository;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRespository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRespository.save(user);

            return true;
        } catch (Exception e) {
//            log.error("Error occured for {}",user.getName(),e.getMessage());
//            log.info(e.getMessage(),"vahooahsdfihjasiufdhasilufdhas");
//            log.trace("vahooahsdfihjasiufdhasilufdhas");
//            log.debug("vahooahsdfihjasiufdhasilufdhas");
//            log.warn("vahooahsdfihjasiufdhasilufdhas");
            return false;
        }

    }
    public void saveUser(User user) {

        userRespository.save(user);
    }
}
