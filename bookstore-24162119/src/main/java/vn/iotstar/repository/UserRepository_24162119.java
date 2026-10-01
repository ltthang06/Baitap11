package vn.iotstar.repository;

import java.util.Optional;

import vn.iotstar.entity.User_24162119;

public interface
        UserRepository_24162119 {

    Optional<User_24162119>
            findByEmail(
                    String email);

    User_24162119 save(
            User_24162119 user);

    boolean existsByEmail(
            String email);
}