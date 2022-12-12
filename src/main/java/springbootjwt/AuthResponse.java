package springbootjwt;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {
}
