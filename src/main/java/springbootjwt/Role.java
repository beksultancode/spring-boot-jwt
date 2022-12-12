package springbootjwt;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CUSTOMER, // satyp aluuchu
    VENDOR; // satuuchu

    @Override
    public String getAuthority() {
        return name();
    }
}
