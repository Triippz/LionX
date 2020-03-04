package edu.psu.lionx.domain;

import edu.psu.lionx.Exceptions.LionxAuthenticationError;
import edu.psu.lionx.repository.impl.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class UserTest {

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
    void getId() {
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
        user.setId(DEFAULT_ID);
        assertEquals(DEFAULT_ID, user.getId());
    }

    @Test
    void getLogin() {
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
        assertEquals(DEFAULT_LOGIN, user.getLogin());
    }

    @Test
    void setLogin() {
        User user = new User(
                "2",
                DEFAULT_PASSWORD,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_EMAIL,
                DEFAULT_PHONE_NUMBER,
                DEFAULT_IMAGE_URL,
                DEFAULT_IS_SIGNED_IN
        );
        user.setLogin(DEFAULT_LOGIN);
        assertEquals(DEFAULT_LOGIN, user.getLogin());
    }

    @Test
    void getPassword() {
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
        user.setPassword(DEFAULT_PASSWORD);
        assertEquals(DEFAULT_PASSWORD, user.getPassword());
    }

    @Test
    void setPassword() {
        User user = new User(
                DEFAULT_LOGIN,
                "DEFAULT_PASSWORD",
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_EMAIL,
                DEFAULT_PHONE_NUMBER,
                DEFAULT_IMAGE_URL,
                DEFAULT_IS_SIGNED_IN
        );
        user.setPassword(DEFAULT_PASSWORD);
        assertEquals(DEFAULT_PASSWORD, user.getPassword());
    }

    @Test
    void getFirstName() {
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
        assertEquals(DEFAULT_FIRST_NAME, user.getFirstName());
    }

    @Test
    void setFirstName() {
        User user = new User(
                DEFAULT_LOGIN,
                DEFAULT_PASSWORD,
                "DEFAULT_FIRST_NAME",
                DEFAULT_LAST_NAME,
                DEFAULT_EMAIL,
                DEFAULT_PHONE_NUMBER,
                DEFAULT_IMAGE_URL,
                DEFAULT_IS_SIGNED_IN
        );
        user.setFirstName(DEFAULT_FIRST_NAME);
        assertEquals(DEFAULT_FIRST_NAME, user.getFirstName());
    }

    @Test
    void getLastName() {
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
        assertEquals(DEFAULT_LAST_NAME, user.getLastName());
    }

    @Test
    void setLastName() {
        User user = new User(
                DEFAULT_LOGIN,
                DEFAULT_PASSWORD,
                DEFAULT_FIRST_NAME,
                "DEFAULT_LAST_NAME",
                DEFAULT_EMAIL,
                DEFAULT_PHONE_NUMBER,
                DEFAULT_IMAGE_URL,
                DEFAULT_IS_SIGNED_IN
        );
        user.setLastName(DEFAULT_LAST_NAME);
        assertEquals(DEFAULT_LAST_NAME, user.getLastName());
    }

    @Test
    void getEmail() {
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
        assertEquals(DEFAULT_EMAIL, user.getEmail());
    }

    @Test
    void setEmail() {
        User user = new User(
                DEFAULT_LOGIN,
                DEFAULT_PASSWORD,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                "DEFAULT_EMAIL",
                DEFAULT_PHONE_NUMBER,
                DEFAULT_IMAGE_URL,
                DEFAULT_IS_SIGNED_IN
        );
        user.setEmail(DEFAULT_EMAIL);
        assertEquals(DEFAULT_EMAIL, user.getEmail());
    }

    @Test
    void getPhoneNumber() {
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
        assertEquals(DEFAULT_PHONE_NUMBER, user.getPhoneNumber());
    }

    @Test
    void setPhoneNumber() {
        User user = new User(
                DEFAULT_LOGIN,
                DEFAULT_PASSWORD,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_EMAIL,
                "DEFAULT_PHONE_NUMBER",
                DEFAULT_IMAGE_URL,
                DEFAULT_IS_SIGNED_IN
        );
        user.setPhoneNumber(DEFAULT_PHONE_NUMBER);
        assertEquals(DEFAULT_PHONE_NUMBER, user.getPhoneNumber());
    }

    @Test
    void getImageUrl() {
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
        assertEquals(DEFAULT_IMAGE_URL, user.getImageUrl());
    }

    @Test
    void setImageUrl() {
        User user = new User(
                DEFAULT_LOGIN,
                DEFAULT_PASSWORD,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_EMAIL,
                DEFAULT_PHONE_NUMBER,
                "DEFAULT_IMAGE_URL",
                DEFAULT_IS_SIGNED_IN
        );
        user.setImageUrl(DEFAULT_IMAGE_URL);
        assertEquals(DEFAULT_IMAGE_URL, user.getImageUrl());
    }

    @Test
    void getSignedIn() {
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
        assertTrue(user.getSignedIn());
    }

    @Test
    void setSignedIn() {
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
        user.setSignedIn(false);
        assertFalse(user.getSignedIn());
    }

    @Test
    void save() {
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
        user.delete();
    }


    @Test
    void delete() throws LionxAuthenticationError {
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
        user.delete();

        Optional<User> optionalUser = userRepository.findByUsername(user.getLogin());
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void autenticate() throws LionxAuthenticationError {
        User user = new User(
                DEFAULT_LOGIN,
                DEFAULT_PASSWORD,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_EMAIL,
                DEFAULT_PHONE_NUMBER,
                DEFAULT_IMAGE_URL,
                false
        );
        user.save();
        assertNotNull(user.getId());

        User.authenticateUser(DEFAULT_LOGIN, DEFAULT_PASSWORD);
        Optional<User> foundUser = userRepository.findUserByIsSignedInIsTrue();
        if ( foundUser.isPresent() ) {
            assertSame(user.getId(), foundUser.get().getId());
            assertTrue(foundUser.get().getSignedIn());
        }

        user.delete();
    }
}