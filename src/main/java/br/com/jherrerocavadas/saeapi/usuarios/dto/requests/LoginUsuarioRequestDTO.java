package br.com.jherrerocavadas.saeapi.usuarios.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUsuarioRequestDTO {
    private String username;
    private String senha;

    public void setSenha(String senha) {
        this.senha = new DigestUtils("SHA3-256").digestAsHex(senha);
    }
}
