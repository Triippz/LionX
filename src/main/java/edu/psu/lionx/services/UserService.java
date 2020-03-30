package edu.psu.lionx.services;

import edu.psu.lionx.Exceptions.LionXUserNotFoundException;
import edu.psu.lionx.domain.User;
import edu.psu.lionx.repository.impl.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

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

    public User getLoggedInUser() throws LionXUserNotFoundException {
        log.debug("Request to find logged in user");
        Optional<User> foundUser = this.userRepository.findUserByIsSignedInIsTrue();
        if ( foundUser.isPresent() )
            return foundUser.get();
        throw new LionXUserNotFoundException("No logged in user found");
    }
}
