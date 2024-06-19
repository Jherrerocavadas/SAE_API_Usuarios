package br.com.jherrerocavadas.saeapi.usuarios.api;

import br.com.jherrerocavadas.saeapi.usuarios.dto.DisciplinaProfessorDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.DisciplinaProfessor;
import br.com.jherrerocavadas.saeapi.usuarios.repository.DisciplinaProfessorRepository;
import br.com.jherrerocavadas.saeapi.usuarios.services.DisciplinaProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class DisciplinaProfessorAPI {



    private final DisciplinaProfessorRepository disciplinaProfessorRepository;

    private final DisciplinaProfessorService disciplinaProfessorService;

    @Autowired
    private DisciplinaProfessorAPI(DisciplinaProfessorRepository disciplinaProfessorRepository,
                                   DisciplinaProfessorService disciplinaProfessorService){
        this.disciplinaProfessorRepository = disciplinaProfessorRepository;
        this.disciplinaProfessorService = disciplinaProfessorService;
    }


    @Operation(summary =  "Retornar todos os professores que possuem cadastro em determinada disciplina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Lista retornada"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/disciplinasProfessores/turmas")
    //Curso Ok
    //Disciplina Ok
    //Aluno com disciplina cursando ou terminada? Ok
    public List<DisciplinaProfessorDTO> retornaProfessoresPorDisciplinaCurso(@RequestParam(value = "curso") String curso,
                                                                     @RequestParam(value = "disciplina") String disciplina,
                                                                     @RequestParam(value = "isEfetivo", required = false) Boolean isEfetivo){
        List<DisciplinaProfessorDTO> disciplinaProfessorDTOList = new ArrayList<>();

        List<DisciplinaProfessor> disciplinaProfessorList = disciplinaProfessorService.findProfessoresInDisciplinaProfessorByFilters(
                curso,
                disciplina,
                isEfetivo,
                null);

        for (DisciplinaProfessor disciplinaProfessor: disciplinaProfessorList) {
            DisciplinaProfessorDTO disciplinaProfessorDTO = disciplinaProfessorService.disciplinaProfessorToDisciplinaProfessorDto(disciplinaProfessor);
            disciplinaProfessorDTOList.add(disciplinaProfessorDTO);
        }

        return disciplinaProfessorDTOList;

    }




    @Operation(summary =  "Retornar todas as disciplinas de determinado aluno baseado nos filtros fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Lista retornada"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/disciplinasProfessores/professor/{numeroMatricula}")
    //Aluno com disciplina cursando ou terminada?
    public List<DisciplinaProfessorDTO> retornarDisciplinaPorProfessor(@PathVariable("numeroMatricula") Long numeroMatricula,
                                                               @RequestParam(value = "isEfetivo", required = false) Boolean isEfetivo){

        List<DisciplinaProfessorDTO> disciplinaProfessorDTOList = new ArrayList<>();

        List<DisciplinaProfessor> disciplinaProfessorList = disciplinaProfessorService.findProfessoresInDisciplinaProfessorByFilters(null, null, isEfetivo, numeroMatricula);

        for (DisciplinaProfessor disciplinaProfessor: disciplinaProfessorList) {
            DisciplinaProfessorDTO disciplinaProfessorDTO = disciplinaProfessorService.disciplinaProfessorToDisciplinaProfessorDto(disciplinaProfessor);
            disciplinaProfessorDTOList.add(disciplinaProfessorDTO);
        }

        return disciplinaProfessorDTOList;
    }


    @Operation(summary =  "Cadastrar um nova relação disciplina-professor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Relação disciplina-professor salva"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/disciplinaProfessor")
    public DisciplinaProfessor salvarDisciplinaProfessor(@RequestBody DisciplinaProfessorDTO disciplinaProfessorDTO){

        return disciplinaProfessorRepository.save(disciplinaProfessorService.disciplinaProfessorDtoToDisciplinaProfessor(disciplinaProfessorDTO));
    }




    @Operation(summary =  "Editar uma relação disciplina-professor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Relação disciplina-professor alterada e salva"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PutMapping("/disciplinaProfessor/{id}")
    public void editarDisciplinaProfessor(@RequestBody DisciplinaProfessorDTO disciplinaProfessorDTO, @PathVariable Long id){
        DisciplinaProfessor disciplinaProfessor = new DisciplinaProfessor();

        disciplinaProfessor.setId(id);
        disciplinaProfessorRepository.save(disciplinaProfessor);
    }



    @Operation(summary =  "Remover uma relação disciplina-professor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Relação disciplina-professor removida"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/disciplinaProfessor/{id}")
    public void removerDisciplinaProfessor(@PathVariable Long id){
        disciplinaProfessorRepository.deleteById(id);
    }

}
