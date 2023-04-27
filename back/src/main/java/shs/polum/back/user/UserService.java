package shs.polum.back.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shs.polum.back.exception.ResourceNotFoundException;
import shs.polum.back.exception.ValidationException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) throws ResourceNotFoundException {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return userRepository.findById(id).get();
    }

    public User createUser(User user) throws ValidationException {
        if (user.getUsername() == null) {
            throw new ValidationException("Username is required");
        } else if (user.getUsername().length() >= 50) {
            throw new ValidationException("Username must be less than 50 characters");
        }

        if (user.getEmail() == null) {
            throw new ValidationException("Email is required");
        } else if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ValidationException("Email already exists");
        } else if (!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ValidationException("Email must be valid");
        } else if (user.getEmail().length() >= 255) {
            throw new ValidationException("Email must be less than 255 characters");
        }

        if (user.getPassword() == null) {
            throw new ValidationException("Password is required");
        } else if (!user.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,24}$")) {
            throw new ValidationException("Password must be between 8 and 24 characters and contain at least one uppercase letter, one lowercase letter, one number, and one special character");
        }

        return userRepository.save(user);
    }

    public void updateUser(User user, Integer id) throws ResourceNotFoundException, ValidationException {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        User originalUser = userRepository.findById(id).get();

        if (user.getUsername() != null && user.getUsername().length() >= 50) {
            throw new ValidationException("Username must be less than 50 characters");
        } else if (user.getUsername() != null)
            originalUser.setUsername(user.getUsername());

        if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ValidationException("Email already exists");
        }else if (user.getEmail() != null && !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ValidationException("Email must be valid");
        } else if (user.getEmail() != null && user.getEmail().length() >= 255) {
            throw new ValidationException("Email must be less than 255 characters");
        } else if (user.getEmail() != null)
            originalUser.setEmail(user.getEmail());

        if (user.getPassword() != null && !user.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,24}$")) {
            throw new ValidationException("Password must be between 8 and 24 characters and contain at least one uppercase letter, one lowercase letter, one number, and one special character");
        } else if (user.getPassword() != null)
            originalUser.setPassword(user.getPassword());

        userRepository.save(originalUser);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}