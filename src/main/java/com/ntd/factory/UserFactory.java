package com.ntd.factory;

import com.ntd.entity.*;

public class UserFactory {
    public static User createPerson(String type, Long id,
                                    String username,
                                    String email,
                                    String password,
                                    String status,
                                    double balance) {
        switch (type.toLowerCase()) {
            case "user":
                return new User(id, username, email, password, status, balance);
            default:
                throw new IllegalArgumentException("Unknown person type: " + type);
        }
    }
}
