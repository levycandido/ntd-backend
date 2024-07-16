package com.ntd.config;

import com.ntd.config.model.LoginRequest;
import com.ntd.config.model.LoginResponse;
import com.ntd.entity.User;
import com.ntd.factory.UserFactory;
import com.ntd.repository.UserRepository;
import com.ntd.service.dao.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            String jwt = jwtUtil.generateToken(authentication);
            return new LoginResponse(jwt);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Override
    public User signup(UserDTO userDTO) {
        //Check if customer already exist
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return null;
        }

        User newUser = UserFactory.createPerson("user",
                null,
                userDTO.getUserName(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getStatus(),
                userDTO.getBalance());

        String hashPassword = passwordEncoder.encode(userDTO.getPassword());
        newUser.setPassword(hashPassword);
        userRepository.save(newUser);

        return newUser;
    }
}
