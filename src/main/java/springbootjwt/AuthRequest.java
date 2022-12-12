package springbootjwt;

import lombok.Builder;

@Builder
public record AuthRequest(String email, String password) { // private final String email
}
