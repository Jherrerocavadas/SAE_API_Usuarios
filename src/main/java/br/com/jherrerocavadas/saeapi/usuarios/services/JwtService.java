package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.entity.DadosTipoUsuario;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private final String ISSUER = "sae-api-usuarios";

    @Value("${jwt-expiration-minutes:10}")
    private Long expirationMinutes;

    public String gerarToken(Usuario usuario){
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer(ISSUER)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(this.gerarExpiracaoToken())
                .setAudience(this.recuperarAudience())
                .claim("roles", usuario.getTipoUsuario().getTipoUsuario())
                .signWith(this.recuperarSignatureKey(usuario.getTipoUsuario()))
                .compact()
                ;
    }

    public void validarToken(String token){

    }

    public Date gerarExpiracaoToken(){
        return Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES));

    }

    public String recuperarAudience(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("X-System-Cod");
    }

    public Key recuperarSignatureKey(DadosTipoUsuario tipoUsuario){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(tipoUsuario.getSecretKey()));
    }
}
