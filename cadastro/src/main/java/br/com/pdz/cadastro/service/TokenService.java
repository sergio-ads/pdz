package br.com.pdz.cadastro.service;

import br.com.pdz.cadastro.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${auth.jwt.secret}")
    private String secret;

    public boolean isTokenValido(String token) {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(this.secret);
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(keyBytes)).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Usuario getUsuario(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                .build()
                .parseClaimsJws(token).getBody();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(claims.get("usuario"), Usuario.class);
    }

}
