package br.com.jherrerocavadas.saeapi.usuarios.api;

import br.com.jherrerocavadas.saeapi.usuarios.dto.AlunoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Aluno;
import br.com.jherrerocavadas.saeapi.usuarios.repository.AlunoRepository;
import br.com.jherrerocavadas.saeapi.usuarios.services.AlunoService;
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
public class AlunoAPI {



    private final AlunoRepository alunoRepository;

    private final AlunoService alunoService;


    @Autowired
    private AlunoAPI(AlunoRepository alunoRepository,
                     AlunoService alunoService){
        this.alunoRepository = alunoRepository;
        this.alunoService = alunoService;
    }

    @Operation(summary =  "Verificar o serviço de disciplinas de um aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Serviço OK"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/check-aluno-service")
    public ResponseEntity<String> verificaServico(){
        return ResponseEntity.ok().body("Serviço operacional");
    }


    @Operation(summary =  "Retornar dados de um aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Aluno retornado"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/alunos/aluno/{numMatricula}")
    //Curso
    //Disciplina
    //Aluno com disciplina cursando ou terminada?
    public ResponseEntity<?> buscarAluno(@PathVariable("numMatricula") Long numMatricula){
        Optional<Aluno> alunoOptional = alunoRepository.findById(numMatricula);
        if (alunoOptional.isEmpty()){
            return ResponseEntity.status(404).body("Cadastro de aluno não encontrado!");
        }
            return ResponseEntity.ok(alunoService.alunoToAlunoDto(alunoOptional.get()));
    }






    @Operation(summary =  "Editar um registro de aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Registro de aluno alterado e salvo"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PutMapping("/aluno/{numMatriculaAluno}")
    public void editarAluno(@RequestBody AlunoDTO alunoDTO,
                            @PathVariable("numMatriculaAluno") Long numMatriculaAluno){
        Aluno aluno = alunoService.alunoDtoToAluno(alunoDTO);
        aluno.setNumMatricula(numMatriculaAluno);
        alunoRepository.save(aluno);
    }



    @Operation(summary =  "Remover um cadastro de aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Registro de aluno removido"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/aluno/{numMatriculaAluno}")
    public void removerAluno(@PathVariable("numMatriculaAluno") Long numMatriculaAluno){
        alunoRepository.deleteById(numMatriculaAluno);
    }

}
