package br.com.jherrerocavadas.saeapi.usuarios.dto;

import br.com.jherrerocavadas.saeapi.usuarios.enums.TipoUsuario;
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
    private String login;
    private String senha;
    private String email;
    private TipoUsuario tipoUsuario; // ALUNO, PROFESSOR, SECRETARIA/ADMINISTRATIVO
    private String fotoUsuario;

    public void setSenha(String senha) {


        this.senha = new DigestUtils("SHA3-256").digestAsHex(senha);

    }

}
