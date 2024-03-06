package br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies;


import br.com.jherrerocavadas.saeapi.usuarios.enums.DiaSemana;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DisciplinaCurso {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Dados da faculdade
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "faculdadeId", referencedColumnName = "id",
//            insertable=false, updatable=false,
            foreignKey = @ForeignKey(name = "UK_DISCIPLINACURSO_FACULDADE", value = ConstraintMode.CONSTRAINT))
    private Faculdade faculdade;


    //Dados do curso
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "cursoId", referencedColumnName = "id",
//            insertable=false, updatable=false,
            foreignKey = @ForeignKey(name = "UK_DISCIPLINACURSO_CURSO", value = ConstraintMode.CONSTRAINT))
    private Curso curso;


    //Dados da disciplina
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "disciplinaId", referencedColumnName = "id",
//            insertable=false, updatable=false,
            foreignKey = @ForeignKey(name = "UK_DISCIPLINACURSO_DISCIPLINA", value = ConstraintMode.CONSTRAINT))

    private Disciplina disciplina;

    /*--------------------< Dados dos horários de aula da disciplina >--------------------*/





    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "horarioAula1_id", referencedColumnName = "id",
//            insertable=false, updatable=false,
            foreignKey = @ForeignKey(name = "UK_DISCIPLINACURSO_HORARIOAULA1", value = ConstraintMode.CONSTRAINT))
    private HorarioAula horaAula1;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "horarioAula2_id", referencedColumnName = "id",
//            insertable=false, updatable=false,
            foreignKey = @ForeignKey(name = "UK_DISCIPLINACURSO_HORARIOAULA2", value = ConstraintMode.CONSTRAINT))
    private HorarioAula horaAula2;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "horarioAula3_id", referencedColumnName = "id",
//            insertable=false, updatable=false,
            foreignKey = @ForeignKey(name = "UK_DISCIPLINACURSO_HORARIOAULA3", value = ConstraintMode.CONSTRAINT))
    private HorarioAula horaAula3;
//
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "horarioAula4_id", referencedColumnName = "id",
//            insertable=false, updatable=false,
            foreignKey = @ForeignKey(name = "UK_DISCIPLINACURSO_HORARIOAULA4", value = ConstraintMode.CONSTRAINT))
    private HorarioAula horaAula4;

    private Integer semestre;

    private DiaSemana diaDeAula1;
    private DiaSemana diaDeAula2;


}
