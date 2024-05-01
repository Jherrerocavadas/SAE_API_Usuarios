package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.dto.ProfessorDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Professor;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import br.com.jherrerocavadas.saeapi.usuarios.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessorService {


    private ProfessorRepository professorRepository;
    private UsuarioService usuarioService;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository,
                            UsuarioService usuarioService) {
        this.professorRepository = professorRepository;
        this.usuarioService = usuarioService;
    }

    //Converter DTO para entidade do banco de dados
    public Professor professorDtoToProfessor(ProfessorDTO professorDTO) {
        Professor professor = new Professor();

        professor.setNumMatricula(professorDTO.getNumMatricula());
//        Optional<Usuario> usuarioOptional = usuarioService.findUsuarioByNumUsuario(professorDTO.getUsuarioId());
//
//        if(usuarioOptional.isPresent()){
//            professor.setUsuario(usuarioOptional.get());
//        }
        return professor;
    }


    // Converter entidade do banco de dados para DTO
    public ProfessorDTO professorToProfessorDto(Professor professor){
        ProfessorDTO professorDTO = new ProfessorDTO();

        professorDTO.setNumMatricula(professor.getNumMatricula());

        return professorDTO;
    }

    public Professor relacionarProfessorPorNumUsuario(Long numUsuario, Professor professor) {

        Optional<Usuario> usuarioOptional = usuarioService.findUsuarioByNumUsuario(numUsuario);

        if(usuarioOptional.isPresent()){
            professor.setUsuario(usuarioOptional.get());
        }

        return professor;
    }

    public Professor findById(Long numMatricula) {

        Optional<Professor> professorOptional = professorRepository.findById(numMatricula);

        if(professorOptional.isEmpty()){
            return null;

        }
        return professorOptional.get();

    }
}
