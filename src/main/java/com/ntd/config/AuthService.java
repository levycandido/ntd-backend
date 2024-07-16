package com.ntd.config;

import com.ntd.config.model.LoginRequest;
import com.ntd.config.model.LoginResponse;
import com.ntd.entity.User;
import com.ntd.service.dao.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    User signup(UserDTO user);
}
