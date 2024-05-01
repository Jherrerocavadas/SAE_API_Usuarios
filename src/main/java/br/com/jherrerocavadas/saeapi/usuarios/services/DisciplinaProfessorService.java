package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.dto.DisciplinaProfessorDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.DisciplinaProfessor;
import br.com.jherrerocavadas.saeapi.usuarios.repository.DisciplinaCursoRepository;
import br.com.jherrerocavadas.saeapi.usuarios.repository.DisciplinaProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaProfessorService {

    private DisciplinaProfessorRepository disciplinaProfessorRepository;
    private DisciplinaCursoRepository disciplinaCursoRepository;
    private ProfessorService professorService;

    @Autowired
    public DisciplinaProfessorService(DisciplinaProfessorRepository disciplinaProfessorRepository,
                                  DisciplinaCursoRepository disciplinaCursoRepository,
                                      ProfessorService professorService) {
        this.disciplinaProfessorRepository = disciplinaProfessorRepository;
        this.disciplinaCursoRepository = disciplinaCursoRepository;
        this.professorService = professorService;
    }

    public List<DisciplinaProfessor> findProfessoresInDisciplinaProfessorByFilters(String curso,
                                                                      String disciplina,
                                                                      Boolean isEfetivo,
                                                                      Long numMatricula) {


        return disciplinaProfessorRepository.findByFilters(curso, disciplina, isEfetivo, numMatricula);
    }

    public DisciplinaProfessorDTO disciplinaProfessorToDisciplinaProfessorDto(DisciplinaProfessor disciplinaProfessor) {

        DisciplinaProfessorDTO disciplinaProfessorDTO = new DisciplinaProfessorDTO();

        disciplinaProfessorDTO.setId(disciplinaProfessor.getId());
        disciplinaProfessorDTO.setNumMatricula(disciplinaProfessor.getProfessor().getNumMatricula());
        disciplinaProfessorDTO.setDisciplinaCursoId(disciplinaProfessor.getDisciplinaCurso().getId());
        disciplinaProfessorDTO.setIsEfetivo(disciplinaProfessor.getIsEfetivo());

        return disciplinaProfessorDTO;
    }

    public DisciplinaProfessor disciplinaProfessorDtoToDisciplinaProfessor(DisciplinaProfessorDTO disciplinaProfessorDTO) {

        DisciplinaProfessor disciplinaProfessor = new DisciplinaProfessor();


        disciplinaProfessor.setId(disciplinaProfessorDTO.getId());
        disciplinaProfessor.setProfessor(professorService.findById(disciplinaProfessorDTO.getNumMatricula()));
        disciplinaProfessor.setDisciplinaCurso(disciplinaCursoRepository.findById(disciplinaProfessorDTO.getDisciplinaCursoId()).get()); //Verificar esse metodo com optional
        disciplinaProfessor.setIsEfetivo(disciplinaProfessorDTO.getIsEfetivo());

        return disciplinaProfessor;
    }
}
