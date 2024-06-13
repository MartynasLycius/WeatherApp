package com.weatherapp.services;

//import com.example.application.data.ContactRepository;
import com.weatherapp.model.User;
import com.weatherapp.dao.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 11 Jun, 2024
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method is used to retrieve a User object by username.
     *
     * @param userName The username of the user to retrieve.
     * @return User Returns the User object with the provided username if found, null otherwise.
     *
     * The method searches the userRepository for a User object with the provided username and returns it.
     */
    public  User getUser(String userName){
        return userRepository.search(userName);
    }

    /**
     * This method is used to authenticate a user.
     *
     * @param userName The username of the user trying to authenticate.
     * @param password The password of the user trying to authenticate.
     * @return boolean Returns true if the user is found in the repository and the password matches, false otherwise.
     *
     * The method first checks if the username or password is null or empty. If either is, it returns false.
     * Then it searches the userRepository for a User object with the provided username.
     * If such a User object is found and the password matches, it returns true.
     * If no such User object is found or the password doesn't match, it returns false.
     */
    public void register(String userName, String password) {
        User user = new User();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        password = passwordEncoder.encode(password);
        user.setUserName(userName);
        user.setPassword(password);
        userRepository.save(user);
    }
}
