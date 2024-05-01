package br.com.jherrerocavadas.saeapi.usuarios.api;

import br.com.jherrerocavadas.saeapi.usuarios.dto.AlunoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.dto.DisciplinaAlunoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.DisciplinaAluno;
import br.com.jherrerocavadas.saeapi.usuarios.repository.DisciplinaAlunoRepository;
import br.com.jherrerocavadas.saeapi.usuarios.services.DisciplinaAlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class DisciplinaAlunoAPI {



    private final DisciplinaAlunoRepository disciplinaAlunoRepository;

    private final DisciplinaAlunoService disciplinaAlunoService;

    @Autowired
    private DisciplinaAlunoAPI(DisciplinaAlunoRepository disciplinaAlunoRepository,
                               DisciplinaAlunoService disciplinaAlunoService){
        this.disciplinaAlunoRepository = disciplinaAlunoRepository;
        this.disciplinaAlunoService = disciplinaAlunoService;
    }

    @Operation(summary =  "Verificar o serviço de disciplinas de um aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Serviço OK"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/check-disciplinas-aluno-service")
    public ResponseEntity<String> verificaServico(){
        return ResponseEntity.ok().body("Serviço operacional");
    }




    @Operation(summary =  "Retornar todos os alunos que possuem cadastro em determinada disciplina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Lista retornada"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/disciplinasAlunos/turmas")
    //Curso Ok
    //Disciplina Ok
    //Aluno com disciplina cursando ou terminada? Ok
    public List<DisciplinaAlunoDTO> retornarAlunosPorDisciplinaCurso(@RequestParam(value = "curso") String curso,
                                                                     @RequestParam(value = "disciplina") String disciplina,
                                                                     @RequestParam(value = "alunoConcluiu", required = false) Boolean isCursada,
                                                                     @RequestParam(value = "alunoDispensado", required = false) Boolean isDispensado
    ){
        List<DisciplinaAlunoDTO> disciplinaAlunoDTOList = new ArrayList<>();

        List<DisciplinaAluno> disciplinaAlunoList = disciplinaAlunoService.findAlunosInDisciplinaAlunoByFilters(
                curso,
                disciplina,
                isCursada,
                isDispensado);

        for (DisciplinaAluno disciplinaAluno: disciplinaAlunoList) {
            DisciplinaAlunoDTO disciplinaAlunoDTO = disciplinaAlunoService.disciplinaAlunoToDisciplinaAlunoDto(disciplinaAluno);
            disciplinaAlunoDTOList.add(disciplinaAlunoDTO);
        }

        return disciplinaAlunoDTOList;

    }




    @Operation(summary =  "Retornar todas as disciplinas de determinado aluno baseado nos filtros fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Lista retornada"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/disciplinasAlunos/aluno/{numeroMatricula}")
    //Aluno com disciplina cursando ou terminada?
    public List<DisciplinaAlunoDTO> retornarDisciplinaPorAluno(@PathVariable("numeroMatricula") Long numeroMatricula,
                                                               @RequestParam(value = "alunoConcluiu", required = false) Boolean isCursada,
                                                               @RequestParam(value = "alunoDispensado", required = false) Boolean isDispensado){

        List<DisciplinaAlunoDTO> disciplinaAlunoDTOList = new ArrayList<>();
        List<DisciplinaAluno> disciplinaAlunoList = disciplinaAlunoService.findAlunosInDisciplinaAlunoByFilters(null, null, isCursada, isDispensado, numeroMatricula);
        for (DisciplinaAluno disciplinaAluno: disciplinaAlunoList) {
            DisciplinaAlunoDTO disciplinaAlunoDTO = disciplinaAlunoService.disciplinaAlunoToDisciplinaAlunoDto(disciplinaAluno);
            disciplinaAlunoDTOList.add(disciplinaAlunoDTO);
        }
        return disciplinaAlunoDTOList;
    }


    @Operation(summary =  "Cadastrar um nova relação disciplina-aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Relação disciplina-aluno salva"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/disciplinaAluno")
    public DisciplinaAluno salvarDisciplinaAluno(@RequestBody DisciplinaAlunoDTO disciplinaAlunoDTO){

        return disciplinaAlunoRepository.save(disciplinaAlunoService.disciplinaAlunoDtoToDisciplinaAluno(disciplinaAlunoDTO));
    }




    @Operation(summary =  "Editar uma relação disciplina-aluno existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Relação disciplina-aluno alterada e salva"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PutMapping("/disciplinaAluno/{id}")
    public void editarDisciplinaAluno(@RequestBody DisciplinaAlunoDTO disciplinaAlunoDTO, @PathVariable Long id){
        DisciplinaAluno disciplinaAluno = new DisciplinaAluno();

        disciplinaAluno.setId(id);
        disciplinaAlunoRepository.save(disciplinaAluno);
    }



    @Operation(summary =  "Remover uma relação disciplina-aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Relação disciplina-aluno removida"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/disciplinaAluno/{id}")
    public void removerDisciplinaAluno(@PathVariable Long id){
        disciplinaAlunoRepository.deleteById(id);
    }

}
