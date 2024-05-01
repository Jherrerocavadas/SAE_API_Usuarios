package br.com.jherrerocavadas.saeapi.usuarios.repository;

import br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies.DisciplinaCurso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaCursoRepository extends JpaRepository<DisciplinaCurso, Long> {
}
