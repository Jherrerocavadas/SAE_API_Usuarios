package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.dto.DisciplinaAlunoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.DisciplinaAluno;
import br.com.jherrerocavadas.saeapi.usuarios.repository.DisciplinaAlunoRepository;
import br.com.jherrerocavadas.saeapi.usuarios.repository.DisciplinaCursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaAlunoService {

    private DisciplinaAlunoRepository disciplinaAlunoRepository;
    private DisciplinaCursoRepository disciplinaCursoRepository;
    private AlunoService alunoService;

    public DisciplinaAlunoService(DisciplinaAlunoRepository disciplinaAlunoRepository,
                                  DisciplinaCursoRepository disciplinaCursoRepository,
                                  AlunoService alunoService) {
        this.disciplinaAlunoRepository = disciplinaAlunoRepository;
        this.disciplinaCursoRepository = disciplinaCursoRepository;
        this.alunoService = alunoService;
    }

    public List<DisciplinaAluno> findAlunosInDisciplinaAlunoByFilters(String curso,
                                                                      String disciplina,
                                                                      Boolean isCursada,
                                                                      Boolean isDispensado,
                                                                      Long numMatricula) {


        return disciplinaAlunoRepository.findByFilters(curso, disciplina, isCursada, isDispensado, numMatricula);
    }

    public DisciplinaAlunoDTO disciplinaAlunoToDisciplinaAlunoDto(DisciplinaAluno disciplinaAluno) {

        DisciplinaAlunoDTO disciplinaAlunoDTO = new DisciplinaAlunoDTO();

        disciplinaAlunoDTO.setId(disciplinaAluno.getId());
        disciplinaAlunoDTO.setNumMatricula(disciplinaAluno.getAluno().getNumMatricula());
        disciplinaAlunoDTO.setDisciplinaCursoId(disciplinaAluno.getDisciplinaCurso().getId());
        disciplinaAlunoDTO.setIsCursada(disciplinaAluno.isCursada());
        disciplinaAlunoDTO.setIsDispensada(disciplinaAluno.isDispensada());

        return disciplinaAlunoDTO;
    }

    public DisciplinaAluno disciplinaAlunoDtoToDisciplinaAluno(DisciplinaAlunoDTO disciplinaAlunoDTO) {


        DisciplinaAluno disciplinaAluno = new DisciplinaAluno();

        disciplinaAluno.setId(disciplinaAlunoDTO.getId());
        disciplinaAluno.setAluno(alunoService.findById(disciplinaAlunoDTO.getNumMatricula()));
        disciplinaAluno.setDisciplinaCurso(disciplinaCursoRepository.findById(disciplinaAlunoDTO.getDisciplinaCursoId()).get());
        disciplinaAluno.setCursada(disciplinaAlunoDTO.getIsCursada());
        disciplinaAluno.setDispensada(disciplinaAlunoDTO.getIsDispensada());

        return disciplinaAluno;
    }
}
