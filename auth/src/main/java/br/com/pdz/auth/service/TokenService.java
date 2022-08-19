package br.com.pdz.auth.service;

import br.com.pdz.auth.model.Usuario;
import br.com.pdz.auth.model.response.UsuarioResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${auth.jwt.expiration}")
    private String expiration;

    @Value("${auth.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        Usuario logado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);

        return Jwts.builder()
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(Keys.hmacShaKeyFor(keyBytes))
                .claim("usuario", new UsuarioResponse(logado))
                .compact();
    }

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
