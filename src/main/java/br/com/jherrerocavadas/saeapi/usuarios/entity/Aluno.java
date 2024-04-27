package br.com.jherrerocavadas.saeapi.usuarios.entity;

import br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies.Curso;
import br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies.Faculdade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder

public class Aluno{

    /** Tabela para armazenar informações específicas do cadastro do aluno,
     * como Percentual de progressão (PP), Percentual de Rendimento (PR),
     * Semestre vigente, Faculdade onde está matriculado, etc**/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long numMatricula;

    private Float percentualProgressao;

    private Float percentualRendimento;

    private Integer semestre;


    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "numCadastroUsuario", referencedColumnName = "numUsuario",
    foreignKey = @ForeignKey(name = "UK_ALUNO_USUARIO", value = ConstraintMode.CONSTRAINT))

    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "faculdadeId", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "UK_ALUNO_FACULDADE", value = ConstraintMode.CONSTRAINT))
    private Faculdade faculdade;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "cursoId", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "UK_ALUNO_CURSO", value = ConstraintMode.CONSTRAINT))
    private Curso curso;


}
