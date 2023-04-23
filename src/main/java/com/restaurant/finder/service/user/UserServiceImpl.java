package com.restaurant.finder.service.user;

import com.restaurant.finder.dto.UserDto;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.enums.Role;
import com.restaurant.finder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 22042023
 *       Copyright (C) 2023 Newcastle University, UK
 */

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        user.setGender(userDto.getGender());
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll().stream().toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (UsernameNotFoundException usernameNotFoundException) {
            throw usernameNotFoundException;
        }
    }
}
