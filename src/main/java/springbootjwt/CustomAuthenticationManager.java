package springbootjwt;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepo.findByEmail(email);
        // user == null
        if (user == null) {
            throw new EntityNotFoundException(
                    String.format("User via email = %s not found", email)
            );
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(
                    "Invalid password"
            );
        }

        return new UsernamePasswordAuthenticationToken(email, user.getPassword(), Collections.singletonList(user.getRole()));
    }
}
