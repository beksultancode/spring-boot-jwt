package springbootjwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtilities {

    private final String secret = "javaseven";

    // generate token // topwiuecfpoiqwjefpmxoijwfo;iacmw;ijfxamo;sifx;asif
    public String generateToken(String email) {
        return JWT.create()
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(secret));
    }

    // validate token
    public String validateToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secret))
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("email").asString();
    }
}
