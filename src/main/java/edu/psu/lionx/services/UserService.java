package edu.psu.lionx.services;

import edu.psu.lionx.repository.impl.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private Logger log = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserService() {
       this.userRepository = new UserRepository();
    }

    public void userLoggedIn(Runnable foundUser, Runnable userNotFound){
        log.debug("Request to find logged in user");
        this.userRepository.findUserByIsSignedInIsTrue()
                .ifPresentOrElse( user -> {
                    log.debug("Logged in User found: {}", user);
                    foundUser.run();
                }, userNotFound);
    }
}
