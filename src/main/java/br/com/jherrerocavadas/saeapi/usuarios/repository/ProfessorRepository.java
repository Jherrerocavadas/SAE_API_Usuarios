package br.com.jherrerocavadas.saeapi.usuarios.repository;

import br.com.jherrerocavadas.saeapi.usuarios.entity.Professor;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    @Query("SELECT PR.usuario from Professor PR where PR.numMatricula = :numMatricula")
    Optional<Usuario> findUsuarioByNumMatricula(@Param("numMatricula") Long numMatriculaUsuario);
}
