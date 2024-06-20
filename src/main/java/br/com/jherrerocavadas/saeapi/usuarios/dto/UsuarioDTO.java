package br.com.jherrerocavadas.saeapi.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    
    private Long numUsuario;
    private String nome;
    private String username;
    private String senha;
    private String email;
    private String tipoUsuario; // ALUNO, PROFESSOR, SECRETARIA/ADMINISTRATIVO
    private String fotoUsuario;

    public void setSenha(String senha) {


        this.senha = new DigestUtils("SHA3-256").digestAsHex(senha);

    }

}
