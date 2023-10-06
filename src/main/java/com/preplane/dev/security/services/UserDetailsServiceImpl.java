package com.preplane.dev.security.services;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.preplane.dev.models.User;
import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.repositories.User.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SQLResult<User> fetchResult = userRepository.findByUsername(username);
        if (fetchResult.statusCode != HttpStatus.OK)
            throw new UsernameNotFoundException("User Not Found with username: " + username);

        return UserDetailsImpl.build(fetchResult.response);
    }

}
