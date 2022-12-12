package springbootjwt;

import com.sun.net.httpserver.Headers;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final TokenUtilities tokenUtilities;
    private final UserRepo userRepo;

    // Bearer
    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {

        // logic
        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            if (StringUtils.hasText(token)) {
                try {
                    String email = tokenUtilities.validateToken(token);
                    User user = userRepo.findByEmail(email);

                    if (user == null) {
                        throw new EntityNotFoundException("Entity not found");
                    }

                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(),
                                    null,
                                    Collections.singleton(user.getRole())
                            )
                    );
                } catch (Exception e) {
                    response.sendError(403, e.getMessage());
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
