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


public class DisciplinaProfessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "professorId", referencedColumnName = "numMatricula",
//    insertable=false, updatable=false,
    foreignKey = @ForeignKey(name = "UK_DISCIPLINAPROFESSOR_PROFESSOR", value = ConstraintMode.CONSTRAINT))
    private Professor professor;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "disciplinaCursoId", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "UK_DISCIPLINAPROFESSOR_DISCIPLINACURSO", value = ConstraintMode.CONSTRAINT))
    private DisciplinaCurso disciplinaCurso;

    private Boolean isEfetivo;

}
