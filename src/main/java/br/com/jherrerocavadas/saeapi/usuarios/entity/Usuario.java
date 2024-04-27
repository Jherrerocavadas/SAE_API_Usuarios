package br.com.jherrerocavadas.saeapi.usuarios.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numUsuario;
    private String nome; //TODO: Separar por nome e sobrenome talvez
    private String login;
    private String senha;
    private String email;

    @Column(columnDefinition = "TINYINT")
    private Integer tipoUsuario; // ALUNO, PROFESSOR, SECRETARIA/ADMINISTRATIVO

    private byte[] fotoUsuario;


}
