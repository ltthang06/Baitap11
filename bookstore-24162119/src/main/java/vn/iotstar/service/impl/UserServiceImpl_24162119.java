package vn.iotstar.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import vn.iotstar.entity.User_24162119;
import vn.iotstar.repository.UserRepository_24162119;
import vn.iotstar.repository.impl.UserRepositoryImpl_24162119;
import vn.iotstar.service.UserService_24162119;
import vn.iotstar.util.PasswordUtil_24162119;

public class
        UserServiceImpl_24162119
        implements
        UserService_24162119 {

    private final UserRepository_24162119
            userRepository;

    public UserServiceImpl_24162119() {

        this.userRepository =
                new UserRepositoryImpl_24162119();
    }

    @Override
    public User_24162119 register(
            String email,
            String fullname,
            Integer phone,
            String password) {

        User_24162119 user =
                new User_24162119();

        user.setEmail(email);
        user.setFullname(fullname);
        user.setPhone(phone);

        user.setPasswd(
                PasswordUtil_24162119
                        .md5(password)
        );

        user.setSignupDate(
                LocalDateTime.now()
        );

        user.setIsAdmin(false);

        return userRepository
                .save(user);
    }

    @Override
    public Optional<User_24162119>
            login(
                    String email,
                    String password) {

        Optional<User_24162119>
                result =
                userRepository
                        .findByEmail(email);

        if (result.isEmpty()) {

            return Optional.empty();
        }

        User_24162119 user =
                result.get();

        String passwordMd5 =
                PasswordUtil_24162119
                        .md5(password);

        if (!passwordMd5.equals(
                user.getPasswd())) {

            return Optional.empty();
        }

        user.setLastLogin(
                LocalDateTime.now()
        );

        userRepository.save(user);

        return Optional.of(user);
    }

    @Override
    public boolean existsByEmail(
            String email) {

        return userRepository
                .existsByEmail(email);
    }
}