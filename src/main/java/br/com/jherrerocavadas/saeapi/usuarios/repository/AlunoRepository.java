package br.com.jherrerocavadas.saeapi.usuarios.repository;

import br.com.jherrerocavadas.saeapi.usuarios.entity.Aluno;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("SELECT AL.usuario from Aluno AL where AL.numMatricula = :numMatricula")
    Optional<Usuario> findUsuarioByNumMatricula(@Param("numMatricula") Long numMatriculaUsuario);

    Aluno findAlunoByUsuario(Usuario usuario);
}
