package br.com.jherrerocavadas.saeapi.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UsuarioLoginResponseDTO {
    private String tokenJwt;
    private Long codigoUsuario;
    private String nome;
    private String email;
    private String tipoUsuario; // ALUNO, PROFESSOR, SECRETARIA/ADMINISTRATIVO
    private String fotoUsuario;

    private Object dadosComplementares;
}
