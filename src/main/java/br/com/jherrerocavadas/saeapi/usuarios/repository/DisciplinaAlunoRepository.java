package br.com.jherrerocavadas.saeapi.usuarios.repository;

import br.com.jherrerocavadas.saeapi.usuarios.dto.DisciplinaAlunoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.DisciplinaAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisciplinaAlunoRepository extends JpaRepository<DisciplinaAluno, Long> {

    @Query("SELECT DA FROM DisciplinaAluno DA " +
            "INNER JOIN Aluno AL ON DA.aluno.numMatricula = AL.numMatricula " +
            "INNER JOIN DisciplinaCurso DC ON DA.disciplinaCurso.id = DC.id " +
            "WHERE(:#{#curso} IS NULL OR (DC.curso.nomeCurso LIKE %:curso% OR " +
            " DC.curso.siglaCurso LIKE %:curso%)) " +
            "AND(:#{#disciplina} IS NULL OR (DC.disciplina.codDisciplina LIKE %:disciplina% OR" +
            " DC.disciplina.nomeDisciplina LIKE %:disciplina% OR DC.disciplina.siglaDisciplina LIKE %:disciplina%)) " +
            "AND(:#{#isCursada} IS NULL OR DA.isCursada = :isCursada) " +
            "AND(:#{#isDispensado} IS NULL OR DA.isDispensado = :isDispensado) ")
    List<DisciplinaAluno> findByFilters(@Param("curso")String curso,
                                           @Param("disciplina")String disciplina,
                                           @Param("isCursada")Boolean isCursada,
                                           @Param("isDispensado")Boolean isDispensado);
}
