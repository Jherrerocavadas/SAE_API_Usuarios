package br.com.jherrerocavadas.saeapi.usuarios.entity;

import br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies.DisciplinaCurso;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Entity
@NoArgsConstructor
@Data

public class DisciplinaAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "alunoId", referencedColumnName = "numMatricula",
//    insertable=false, updatable=false,
    foreignKey = @ForeignKey(name = "UK_DISCIPLINAALUNO_ALUNO", value = ConstraintMode.CONSTRAINT))
    private Aluno aluno;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "disciplinaCursoId", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "UK_DISCIPLINAALUNO_DISCIPLINACURSO", value = ConstraintMode.CONSTRAINT))
    private DisciplinaCurso disciplinaCurso;

    @Nullable
    private boolean isCursada;

    @Nullable
    private boolean isDispensado;
}
