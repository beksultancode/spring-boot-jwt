package springbootjwt;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // @Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User save(User newUser) {
        String password = newUser.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        newUser.setRole(Role.CUSTOMER);
        return userRepo.save(newUser);
    }

    public void delete(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new EntityNotFoundException(
                    String.format("User via id = %d not found", userId));
        }

        userRepo.deleteById(userId);
    }
}
