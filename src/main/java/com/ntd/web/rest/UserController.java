package com.ntd.web.rest;

import com.ntd.ObjectMapperUtils;
import com.ntd.entity.User;
import com.ntd.service.UserService;
import com.ntd.service.dao.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<?> getUserDetails(@RequestBody UserDTO userDTO) {

        User userReturned = userService.findByEmail(userDTO.getEmail());

        return ResponseEntity.ok(ObjectMapperUtils.map(userReturned, UserDTO.class));

    }
}
