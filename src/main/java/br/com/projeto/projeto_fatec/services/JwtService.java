package br.com.projeto.projeto_fatec.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiracao;

    public String extrairUsername(String token) {
        return resgateExtracao(token, Claims::getSubject);
    }

    public <T> T resgateExtracao(String token, Function<Claims, T> resgatesResolver) {
        final Claims resgates = resgatarTodasExtracoes(token);
        return resgatesResolver.apply(resgates);
    }

    public String gerarToken(UserDetails detalhesUsuario) {
        return gerarToken(new HashMap<>(), detalhesUsuario);
    }

    public String gerarToken(Map<String, Object> resgatesExtra, UserDetails detalhesUsuario) {
        return construirToken(resgatesExtra, detalhesUsuario, jwtExpiracao);
    }

    public long getTempoExpiracao() {
        return jwtExpiracao;
    }

    private String construirToken(
            Map<String, Object> resgatesExtra,
            UserDetails detalhesUsuario,
            long expiracao) {
        resgatesExtra.put("ativo", detalhesUsuario.isEnabled());
        return Jwts
                .builder()
                .setClaims(resgatesExtra)
                .setSubject(detalhesUsuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiracao))
                .signWith(getChaveAcesso(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean tokenValido(String token, UserDetails detalhesUsuario) {
        final String username = extrairUsername(token);
        return (username.equals(detalhesUsuario.getUsername())) && !tokenExpirado(token);
    }

    private boolean tokenExpirado(String token) {
        return extrairExpiracao(token).before(new Date());
    }

    private Date extrairExpiracao(String token) {
        return resgateExtracao(token, Claims::getExpiration);
    }

    private Claims resgatarTodasExtracoes(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getChaveAcesso())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getChaveAcesso() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
