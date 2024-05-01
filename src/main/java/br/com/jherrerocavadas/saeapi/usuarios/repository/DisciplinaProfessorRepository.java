package br.com.jherrerocavadas.saeapi.usuarios.repository;

import br.com.jherrerocavadas.saeapi.usuarios.entity.DisciplinaProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisciplinaProfessorRepository extends JpaRepository<DisciplinaProfessor, Long> {

    @Query("SELECT DPR FROM DisciplinaProfessor DPR " +
            "INNER JOIN Professor PR ON DPR.professor.numMatricula = PR.numMatricula " +
            "INNER JOIN DisciplinaCurso DC ON DPR.disciplinaCurso.id = DC.id " +
            "WHERE(:#{#curso} IS NULL OR (DC.curso.nomeCurso LIKE %:curso% OR " +
            " DC.curso.siglaCurso LIKE %:curso%)) " +
            "AND(:#{#disciplina} IS NULL OR (DC.disciplina.codDisciplina LIKE %:disciplina% OR" +
            " DC.disciplina.nomeDisciplina LIKE %:disciplina% OR DC.disciplina.siglaDisciplina LIKE %:disciplina%)) " +
            "AND(:#{#isEfetivo} IS NULL OR DPR.isEfetivo = :isEfetivo)" +
            "AND(:#{#numMatricula} IS NULL OR PR.numMatricula = :numMatricula)")
    List<DisciplinaProfessor> findByFilters(@Param("curso")String curso,
                                           @Param("disciplina")String disciplina,
                                           @Param("isEfetivo")Boolean isEfetivo,
                                        @Param("numMatricula") Long numMatricula);
}
