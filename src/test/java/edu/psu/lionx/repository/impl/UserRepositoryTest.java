package edu.psu.lionx.repository.impl;

import edu.psu.lionx.Exceptions.LionxAuthenticationError;
import edu.psu.lionx.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    public static long DEFAULT_ID = 1L;
    public static String DEFAULT_LOGIN = "johndoe";
    public static String DEFAULT_PASSWORD = "test";
    public static String DEFAULT_FIRST_NAME = "John";
    public static String DEFAULT_LAST_NAME = "Doe";
    public static String DEFAULT_EMAIL = "John.Doe@example.com";
    public static String DEFAULT_PHONE_NUMBER = "+18888888888";
    public static String DEFAULT_IMAGE_URL = "";
    public static Boolean DEFAULT_IS_SIGNED_IN = true;

    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository = new UserRepository();
    }

    @Test
    void findAll() throws Exception {
        User user = new User(
                DEFAULT_LOGIN,
                DEFAULT_PASSWORD,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_EMAIL,
                DEFAULT_PHONE_NUMBER,
                DEFAULT_IMAGE_URL,
                DEFAULT_IS_SIGNED_IN
        );
        user.save();

        List<User> userList = userRepository.findAll();
        assertFalse(userList.isEmpty());
        user.delete();
    }

    @Test
    void testFind() {
        User user = new User(
                DEFAULT_LOGIN,
                DEFAULT_PASSWORD,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_EMAIL,
                DEFAULT_PHONE_NUMBER,
                DEFAULT_IMAGE_URL,
                DEFAULT_IS_SIGNED_IN
        );
        user.save();
        assertNotNull(user.getId());

        Optional<User> foundUser = userRepository.find(user);
        assert (foundUser.isPresent());

        user.delete();
    }

    @Test
    void findUserByIsSignedInIsTrue() {
        User user = new User(
                DEFAULT_LOGIN,
                DEFAULT_PASSWORD,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_EMAIL,
                DEFAULT_PHONE_NUMBER,
                DEFAULT_IMAGE_URL,
                DEFAULT_IS_SIGNED_IN
        );
        user.save();
        assertNotNull(user.getId());

        Optional<User> userByIsSignedInIsTrue = this.userRepository.findUserByIsSignedInIsTrue();
        assertTrue(userByIsSignedInIsTrue.isPresent());

        user.delete();
    }

    @Test
    void findByUsername() throws LionxAuthenticationError {
        User user = new User(
                DEFAULT_LOGIN,
                DEFAULT_PASSWORD,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_EMAIL,
                DEFAULT_PHONE_NUMBER,
                DEFAULT_IMAGE_URL,
                DEFAULT_IS_SIGNED_IN
        );
        user.save();
        assertNotNull(user.getId());

        Optional<User> byUsername = this.userRepository.findByUsername(DEFAULT_LOGIN);
        assertTrue(byUsername.isPresent());
        user.delete();
    }
}