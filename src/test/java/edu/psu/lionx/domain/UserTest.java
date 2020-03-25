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
    private final String DEFAULT_PUBLIC_KEY = "GDEHTFP6JKE7PVCPA2RQT35JLPQLYC535PNCLBWFECLRGTCTBLVCUE5K";
    private final String DEFAULT_PRIVATE_KEY = "SC3FP6NW6BHPJDDAQZZTRVEKWWYFT6RQHW46A6TPXEAXZMLRKUQ72OJR";

    private UserRepository userRepository;
    private Wallet wallet;

    @BeforeEach
    void init() {
        userRepository = new UserRepository();
        wallet = new Wallet(DEFAULT_PUBLIC_KEY, DEFAULT_PRIVATE_KEY);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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
        user.addWallet(wallet);
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

    @Test
    void signOut()  {
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
        user.addWallet(wallet);
        user.save();
        assertNotNull(user.getId());

        user.signOut();

        assertFalse(user.getSignedIn());

        user.delete();
    }
}