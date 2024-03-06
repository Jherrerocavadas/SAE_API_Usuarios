package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.dto.DisciplinaAlunoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.DisciplinaAluno;
import br.com.jherrerocavadas.saeapi.usuarios.repository.DisciplinaAlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaAlunoService {

    private DisciplinaAlunoRepository disciplinaAlunoRepository;

    public DisciplinaAlunoService(DisciplinaAlunoRepository disciplinaAlunoRepository) {
        this.disciplinaAlunoRepository = disciplinaAlunoRepository;
    }

    public List<DisciplinaAluno> findAlunosInDisciplinaAlunoByFilters(String curso,
                                                                      String disciplina,
                                                                      Boolean isCursada,
                                                                      Boolean isDispensado) {


        return disciplinaAlunoRepository.findByFilters(curso, disciplina, isCursada, isDispensado);
    }

    public DisciplinaAlunoDTO disciplinaAlunoToDisciplinaAlunoDto(DisciplinaAluno disciplinaAluno) {

        DisciplinaAlunoDTO disciplinaAlunoDTO = new DisciplinaAlunoDTO();

        disciplinaAlunoDTO.setId(disciplinaAluno.getId());
        disciplinaAlunoDTO.setNumMatricula(disciplinaAluno.getAluno().getNumMatricula());
        disciplinaAlunoDTO.setDisciplinaCursoId(disciplinaAluno.getDisciplinaCurso().getId());
        disciplinaAlunoDTO.setCursada(disciplinaAlunoDTO.isCursada());
        disciplinaAlunoDTO.setDispensada(disciplinaAluno.isDispensado());

        return disciplinaAlunoDTO;
    }
}
