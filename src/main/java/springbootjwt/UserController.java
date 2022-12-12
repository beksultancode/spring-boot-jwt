package springbootjwt;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // find all users
    @GetMapping
    List<User> findAll() {
        return userService.findAllUsers();
    }

    // save new user
    @PostMapping
    User save(@RequestBody User user) {
        return userService.save(user);
    }

    // delete the user
    @DeleteMapping("/{userId}") // http://localhost:8080/api/users/1
    void deleteById(@PathVariable Long userId) {
        userService.delete(userId);
    }
}
