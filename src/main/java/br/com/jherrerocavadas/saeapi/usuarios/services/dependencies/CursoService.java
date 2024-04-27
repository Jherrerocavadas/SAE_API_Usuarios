package br.com.jherrerocavadas.saeapi.usuarios.services.dependencies;

import br.com.jherrerocavadas.saeapi.usuarios.dto.dependenciesDTO.CursoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies.Curso;
import org.springframework.stereotype.Service;

@Service
public class CursoService {
    public Curso cursoDtoToCurso(CursoDTO cursoDTO){
        Curso curso = new Curso();

        curso.setId(cursoDTO.getId());
        curso.setNomeCurso(cursoDTO.getNomeCurso());
        curso.setSiglaCurso(cursoDTO.getSiglaCurso());
        curso.setQtdSemestres(cursoDTO.getQtdSemestres());

        return curso;
    }


    // Converter entidade do banco de dados para DTO
    public CursoDTO cursoToCursoDto(Curso curso){
        CursoDTO cursoDTO = new CursoDTO();

        cursoDTO.setId(curso.getId());
        cursoDTO.setNomeCurso(curso.getNomeCurso());
        cursoDTO.setSiglaCurso(curso.getSiglaCurso());
        cursoDTO.setQtdSemestres(curso.getQtdSemestres());

        return cursoDTO;
    }
}
