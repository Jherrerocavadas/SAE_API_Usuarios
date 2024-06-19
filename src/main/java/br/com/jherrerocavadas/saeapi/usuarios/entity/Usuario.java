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
    private String username;
    private String senha;
    private String email;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "tipoUsuario", referencedColumnName = "tipoUsuario",
            foreignKey = @ForeignKey(name = "usuario_tipo_usuario_FK", value = ConstraintMode.CONSTRAINT))
    private DadosTipoUsuario tipoUsuario; // ALUNO, PROFESSOR, SECRETARIA/ADMINISTRATIVO

    //Identificar o indivíduo caso ele tenha um cadastro já
//    private String cpf;

    private byte[] fotoUsuario;


}
