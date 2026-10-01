package vn.iotstar.service;

import java.util.Optional;

import vn.iotstar.entity.User_24162119;

public interface
        UserService_24162119 {

    User_24162119 register(
            String email,
            String fullname,
            Integer phone,
            String password);

    Optional<User_24162119>
            login(
                    String email,
                    String password);

    boolean existsByEmail(
            String email);
}