package br.com.jherrerocavadas.saeapi.usuarios.api;

import br.com.jherrerocavadas.saeapi.usuarios.dto.ProfessorDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Professor;
import br.com.jherrerocavadas.saeapi.usuarios.repository.ProfessorRepository;
import br.com.jherrerocavadas.saeapi.usuarios.services.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
public class ProfessorAPI {



    private final ProfessorRepository professorRepository;

    private final ProfessorService professorService;

    @Autowired
    private ProfessorAPI(ProfessorRepository professorRepository, ProfessorService professorService){
        this.professorRepository = professorRepository;
        this.professorService = professorService;
    }


    @Operation(summary =  "Criar um cadastro de professor para um usuário já existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =  "Professor vinculado ao usuário informado"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/professores/professor/{numUsuario}")
    public ResponseEntity<Professor> cadastrarProfessorParaUsuario(
            @RequestBody ProfessorDTO professorDTO,
            @PathVariable("numUsuario") Long numUsuario){

        Professor professor = professorService.professorDtoToProfessor(professorDTO);
        professor = professorService.relacionarProfessorPorNumUsuario(numUsuario, professor);

        professor = professorRepository.save(professor); //Atualizar a entidade do professor com o número da matrícula atribuido

        return ResponseEntity.ok(professor);

    }

    @Operation(summary =  "Retornar dados de um professor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Professor retornado"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/professores/professor/{numMatricula}")
    //Curso
    //Disciplina
    //Professor com disciplina cursando ou terminada?
    public ResponseEntity<?> buscarProfessor(@PathVariable("numMatricula") Long numMatricula){
        Optional<Professor> professorOptional = professorRepository.findById(numMatricula);
        if (professorOptional.isEmpty()){
            return ResponseEntity.status(404).body("Cadastro de professor não encontrado!");
        }
            return ResponseEntity.ok(professorService.professorToProfessorDto(professorOptional.get()));
    }






    @Operation(summary =  "Editar um registro de professor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Registro de professor alterado e salvo"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PutMapping("/professor/{numMatriculaProfessor}")
    public void editarProfessor(@RequestBody ProfessorDTO professorDTO,
                            @PathVariable("numMatriculaProfessor") Long numMatriculaProfessor){
        Professor professor = professorService.professorDtoToProfessor(professorDTO);
        professor.setNumMatricula(numMatriculaProfessor);
        professorRepository.save(professor);
    }



    @Operation(summary =  "Remover um cadastro de professor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Registro de professor removido"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/professor/{numMatriculaProfessor}")
    public void removerProfessor(@PathVariable("numMatriculaProfessor") Long numMatriculaProfessor){
        professorRepository.deleteById(numMatriculaProfessor);
    }

}
