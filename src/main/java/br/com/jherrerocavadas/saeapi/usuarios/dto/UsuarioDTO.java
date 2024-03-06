package br.com.jherrerocavadas.saeapi.usuarios.dto;

import br.com.jherrerocavadas.saeapi.usuarios.enums.TipoUsuario;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;


@Data
public class UsuarioDTO {
    
    private Long numUsuario;
    private String nome;
    private String login;
    private String senha;
    private String email;
    private TipoUsuario tipoUsuario; // ALUNO, PROFESSOR, SECRETARIA/ADMINISTRATIVO

    public void setSenha(String senha) {


        this.senha = new DigestUtils("SHA3-256").digestAsHex(senha);

    }

}
