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




    @Operation(summary =  "Retornar todos os alunos que possuem cadastro em determinada disciplina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Lista retornada"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/disciplinasAlunos/aluno/{nomeAluno}")
    //Curso
    //Disciplina
    //Aluno com disciplina cursando ou terminada?
    public List<DisciplinaAlunoDTO> retornarDisciplinaPorAluno(){
        //        List<DisciplinaCurso> disciplinasPorCurso = disciplinaCursoRepository.findAll();
        List<DisciplinaAlunoDTO> disciplinaAlunoDTO = new ArrayList<>();
//        for (DisciplinaCurso disciplinaCurso: disciplinasPorCurso) {
//            DisciplinaCursoDTO disciplinaCursoDTO = new DisciplinaCursoDTO();
//            List<HorarioAula> horasAula = new ArrayList<>();
//            List<DiaSemana> diasAula = new ArrayList<>();
//
//            horasAula.add(disciplinaCurso.getHoraAula1());
//            horasAula.add(disciplinaCurso.getHoraAula2());
//            horasAula.add(disciplinaCurso.getHoraAula3());
//            horasAula.add(disciplinaCurso.getHoraAula4());
//
//            diasAula.add(disciplinaCurso.getDiaDeAula1());
//            diasAula.add(disciplinaCurso.getDiaDeAula2());
//
//            disciplinaCursoDTO.setId(disciplinaCurso.getId());
//            disciplinaCursoDTO.setDisciplina(disciplinaCurso.getDisciplina());
//            disciplinaCursoDTO.setFaculdade(disciplinaCurso.getFaculdade());
//            disciplinaCursoDTO.setSemestre(disciplinaCurso.getSemestre());
//            disciplinaCursoDTO.setCurso(disciplinaCurso.getCurso());
//            disciplinaCursoDTO.setHorasAula(horasAula);
//            disciplinaCursoDTO.setDiasDeAula(diasAula);
//
//            disciplinasCursoDTO.add(disciplinaCursoDTO);
//        }
        return disciplinaAlunoDTO;
    }


//    @Operation(summary =  "Retornar todas as disciplinas de registradas para determinado nome de curso")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description =  "Disciplinas do curso retornadas"),
//            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
//            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
//    })
//    @GetMapping("/disciplinasCursos/cursos/{nomeCurso}")
//    public ResponseEntity<List<DisciplinaCursoDTO>> retornarDisciplinaCursoPorNomeCurso(@PathVariable String nomeCurso){
//        List<DisciplinaCurso> disciplinasPorCurso = disciplinaCursoRepository.getDisciplinaCursoByCursoNomeCurso(nomeCurso);
//        List<DisciplinaCursoDTO> disciplinasCursoDTO = new ArrayList<>();
//        if(Objects.nonNull(disciplinasPorCurso)){
//
//            for (DisciplinaCurso disciplinaCurso: disciplinasPorCurso) {
//                DisciplinaCursoDTO disciplinaCursoDTO = new DisciplinaCursoDTO();
//                List<HorarioAula> horasAula = new ArrayList<>();
//                List<DiaSemana> diasAula = new ArrayList<>();
//
//                horasAula.add(disciplinaCurso.getHoraAula1());
//                horasAula.add(disciplinaCurso.getHoraAula2());
//
//                if(Objects.nonNull(disciplinaCurso.getHoraAula3()) && Objects.nonNull(disciplinaCurso.getHoraAula4())){
//                    horasAula.add(disciplinaCurso.getHoraAula3());
//                    horasAula.add(disciplinaCurso.getHoraAula4());
//                }
//
//                diasAula.add(disciplinaCurso.getDiaDeAula1());
//                if(Objects.nonNull(disciplinaCurso.getDiaDeAula2())){
//                    diasAula.add(disciplinaCurso.getDiaDeAula2());
//                }
//
//
//                disciplinaCursoDTO.setId(disciplinaCurso.getId());
//                disciplinaCursoDTO.setDisciplina(disciplinaCurso.getDisciplina());
//                disciplinaCursoDTO.setFaculdade(disciplinaCurso.getFaculdade());
//                disciplinaCursoDTO.setSemestre(disciplinaCurso.getSemestre());
//                disciplinaCursoDTO.setCurso(disciplinaCurso.getCurso());
//                disciplinaCursoDTO.setHorasAula(horasAula);
//                disciplinaCursoDTO.setDiasDeAula(diasAula);
//
//                disciplinasCursoDTO.add(disciplinaCursoDTO);
//            }
//
//
//
//            return ResponseEntity.ok(disciplinasCursoDTO);
//        }
//        else{
//            return ResponseEntity.notFound().build();
//        }
//    }

//
//    @Operation(summary =  "Retornar todas as disciplinas de registradas para determinado curso (pesquisa por sigla do curso)")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description =  "Disciplinas do curso retornadas"),
//            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
//            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
//    })
//    @GetMapping("/disciplinasCursos/cursos")
//    public ResponseEntity<List<DisciplinaCursoDTO>> retornarDisciplinaCursoPorSiglaCurso(@Param("siglaCurso") String siglaCurso){
//        List<DisciplinaCurso> disciplinasPorCurso = disciplinaCursoRepository.getDisciplinaCursoByCursoSiglaCurso(siglaCurso);
//        List<DisciplinaCursoDTO> disciplinasCursoDTO = new ArrayList<>();
//        if(Objects.nonNull(disciplinasPorCurso)){
//
//            for (DisciplinaCurso disciplinaCurso: disciplinasPorCurso) {
//                DisciplinaCursoDTO disciplinaCursoDTO = new DisciplinaCursoDTO();
//                List<HorarioAula> horasAula = new ArrayList<>();
//                List<DiaSemana> diasAula = new ArrayList<>();
//
//                horasAula.add(disciplinaCurso.getHoraAula1());
//                horasAula.add(disciplinaCurso.getHoraAula2());
//
//                if(Objects.nonNull(disciplinaCurso.getHoraAula3()) && Objects.nonNull(disciplinaCurso.getHoraAula4())){
//                    horasAula.add(disciplinaCurso.getHoraAula3());
//                    horasAula.add(disciplinaCurso.getHoraAula4());
//                }
//
//                diasAula.add(disciplinaCurso.getDiaDeAula1());
//                if(Objects.nonNull(disciplinaCurso.getDiaDeAula2())){
//                    diasAula.add(disciplinaCurso.getDiaDeAula2());
//                }
//
//
//                disciplinaCursoDTO.setId(disciplinaCurso.getId());
//                disciplinaCursoDTO.setDisciplina(disciplinaCurso.getDisciplina());
//                disciplinaCursoDTO.setFaculdade(disciplinaCurso.getFaculdade());
//                disciplinaCursoDTO.setSemestre(disciplinaCurso.getSemestre());
//                disciplinaCursoDTO.setCurso(disciplinaCurso.getCurso());
//                disciplinaCursoDTO.setHorasAula(horasAula);
//                disciplinaCursoDTO.setDiasDeAula(diasAula);
//
//                disciplinasCursoDTO.add(disciplinaCursoDTO);
//            }
//
//
//
//            return ResponseEntity.ok(disciplinasCursoDTO);
//        }
//        else{
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @Operation(summary =  "Retornar todas as disciplinas de registradas para determinado curso (pesquisa por sigla do curso)")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description =  "Disciplinas do curso retornadas"),
//            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
//            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
//    })
//    @GetMapping("/disciplinasCursos/cursos/teste")
//    public ResponseEntity<List<DisciplinaCursoDTO>> retornarTeste(@Param("siglaCurso") String siglaCurso, @Param("codFaculdade") String codFaculdade){
//        List<DisciplinaCurso> disciplinasPorCurso = disciplinaCursoRepository.getDisciplinaCursoByCursoSiglaCursoAndFaculdadeCodFaculdade(siglaCurso, codFaculdade);
//        List<DisciplinaCursoDTO> disciplinasCursoDTO = new ArrayList<>();
//        if(Objects.nonNull(disciplinasPorCurso)){
//
//            for (DisciplinaCurso disciplinaCurso: disciplinasPorCurso) {
//                DisciplinaCursoDTO disciplinaCursoDTO = new DisciplinaCursoDTO();
//                List<HorarioAula> horasAula = new ArrayList<>();
//                List<DiaSemana> diasAula = new ArrayList<>();
//
//                horasAula.add(disciplinaCurso.getHoraAula1());
//                horasAula.add(disciplinaCurso.getHoraAula2());
//                horasAula.add(disciplinaCurso.getHoraAula3());
//                horasAula.add(disciplinaCurso.getHoraAula4());
//
//                diasAula.add(disciplinaCurso.getDiaDeAula1());
//                diasAula.add(disciplinaCurso.getDiaDeAula2());
//
//                disciplinaCursoDTO.setId(disciplinaCurso.getId());
//                disciplinaCursoDTO.setDisciplina(disciplinaCurso.getDisciplina());
//                disciplinaCursoDTO.setFaculdade(disciplinaCurso.getFaculdade());
//                disciplinaCursoDTO.setSemestre(disciplinaCurso.getSemestre());
//                disciplinaCursoDTO.setCurso(disciplinaCurso.getCurso());
//                disciplinaCursoDTO.setHorasAula(horasAula);
//                disciplinaCursoDTO.setDiasDeAula(diasAula);
//
//                disciplinasCursoDTO.add(disciplinaCursoDTO);
//            }
//
//
//
//            return ResponseEntity.ok(disciplinasCursoDTO);
//        }
//        else{
//            return ResponseEntity.notFound().build();
//        }
//    }



    @Operation(summary =  "Cadastrar um nova relação disciplina-aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Relação disciplina-aluno salva"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/disciplinaAluno")
    public void salvarDisciplinaAluno(@RequestBody DisciplinaAlunoDTO disciplinaAlunoDTO){
        DisciplinaAluno disciplinaAluno = new DisciplinaAluno();
////        disciplinaCurso.setFaculdade(new Faculdade(disciplinaCursoDTO.getFaculdadeId()));
////        disciplinaCurso.setCurso(new Curso(disciplinaCursoDTO.getCursoId()));
////        disciplinaCurso.setDisciplina(new Disciplina(disciplinaCursoDTO.getDisciplinaId()));
//
//
//        disciplinaCurso.setFaculdade(disciplinaCursoDTO.getFaculdade());
//        disciplinaCurso.setCurso(disciplinaCursoDTO.getCurso());
//        disciplinaCurso.setDisciplina(disciplinaCursoDTO.getDisciplina());
////        disciplinaCurso.setHoraAula1(new HorarioAula(disciplinaCursoDTO.getHorasAula().get(0).getId()));
////        disciplinaCurso.setHoraAula1(new HorarioAula(disciplinaCursoDTO.getHorasAula().get(1).getId()));
////        disciplinaCurso.setHoraAula2(new HorarioAula(disciplinaCursoDTO.getHorasAula().get(2).getId()));
////        disciplinaCurso.setHoraAula3(new HorarioAula(disciplinaCursoDTO.getHorasAula().get(3).getId()));
//
//        if(!disciplinaCursoDTO.getHorasAula().isEmpty()){
//            disciplinaCurso.setHoraAula1(disciplinaCursoDTO.getHorasAula().get(0));
//            disciplinaCurso.setHoraAula2(disciplinaCursoDTO.getHorasAula().get(1));
//            disciplinaCurso.setDiaDeAula1(disciplinaCursoDTO.getDiasDeAula().get(0));
//            disciplinaCurso.setSemestre(disciplinaCursoDTO.getSemestre());
//
//            if (disciplinaCursoDTO.getHorasAula().size() == 4) {
//                disciplinaCurso.setHoraAula3(disciplinaCursoDTO.getHorasAula().get(2));
//                disciplinaCurso.setHoraAula4(disciplinaCursoDTO.getHorasAula().get(3));
//
//            }
//
//            if(disciplinaCursoDTO.getDiasDeAula().size() == 2){
//                disciplinaCurso.setDiaDeAula2(disciplinaCursoDTO.getDiasDeAula().get(1));
//            }
//        }
//
//
//        log.info(String.valueOf(disciplinaCurso));

        disciplinaAlunoRepository.save(disciplinaAluno);
    }




    @Operation(summary =  "Editar uma relação disciplina-aluni existente")
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



    /* COLOCAR ISSO NO DISCIPLINA ALUNO API */
//    @Operation(summary =  "Retornar todas as disciplinas de um aluno")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description =  "Lista retornada"),
//            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
//    })
//    @GetMapping("/alunos/aluno/{numMatricula}")
//    //Curso
//    //Disciplina
//    //Aluno com disciplina cursando ou terminada?
//    public List<AlunoDTO> retornarDisciplinaPorAluno(@PathVariable("numMatricula") Long numMatricula){
//        List<AlunoDTO> alunoDTO = new ArrayList<>();
//
//        return alunoDTO;
//    }



//    @Operation(summary =  "Retornar todos os alunos que possuem cadastro em determinada disciplina")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description =  "Lista retornada"),
//            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
//    })
//    @GetMapping("/alunos/turmas")
//    //Curso
//    //Disciplina
//    //Aluno com disciplina cursando ou terminada?
//    public List<AlunoDTO> retornarAlunosPorDisciplinaCurso(){
////        List<DisciplinaCurso> disciplinasPorCurso = disciplinaCursoRepository.findAll();
//        List<AlunoDTO> alunoDTO = new ArrayList<>();
////        for (DisciplinaCurso disciplinaCurso: disciplinasPorCurso) {
////            DisciplinaCursoDTO disciplinaCursoDTO = new DisciplinaCursoDTO();
////            List<HorarioAula> horasAula = new ArrayList<>();
////            List<DiaSemana> diasAula = new ArrayList<>();
////
////            horasAula.add(disciplinaCurso.getHoraAula1());
////            horasAula.add(disciplinaCurso.getHoraAula2());
////            horasAula.add(disciplinaCurso.getHoraAula3());
////            horasAula.add(disciplinaCurso.getHoraAula4());
////
////            diasAula.add(disciplinaCurso.getDiaDeAula1());
////            diasAula.add(disciplinaCurso.getDiaDeAula2());
////
////            disciplinaCursoDTO.setId(disciplinaCurso.getId());
////            disciplinaCursoDTO.setDisciplina(disciplinaCurso.getDisciplina());
////            disciplinaCursoDTO.setFaculdade(disciplinaCurso.getFaculdade());
////            disciplinaCursoDTO.setSemestre(disciplinaCurso.getSemestre());
////            disciplinaCursoDTO.setCurso(disciplinaCurso.getCurso());
////            disciplinaCursoDTO.setHorasAula(horasAula);
////            disciplinaCursoDTO.setDiasDeAula(diasAula);
////
////            disciplinasCursoDTO.add(disciplinaCursoDTO);
////        }
//        return alunoDTO;
//    }





    @Operation(summary =  "Cadastrar um nova relação disciplina-aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Relação disciplina-aluno salva"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/aluno/{numMatriculaAluno}/disciplinas/{codDisciplina}")
    public void salvarAluno(@RequestBody AlunoDTO alunoDTO,
                            @PathVariable("numMatriculaAluno") Long numMatriculaAluno,
                            @PathVariable("codDisciplina") Long codDisciplina){

    }

//
//    @Operation(summary =  "Editar uma relação disciplina-aluno existente")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description =  "Relação disciplina-aluno alterada e salva"),
//            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
//            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
//    })
//    @PutMapping("/aluno/{id}")
//    public void editarAluno(@RequestBody AlunoDTO alunoDTO, @PathVariable Long id){
//        Aluno aluno = alunoService.alunoDtoToAluno(alunoDTO);
//        aluno.setNumMatricula(id);
//        alunoRepository.save(aluno);
//    }
//
//
//
//    @Operation(summary =  "Remover uma relação disciplina-aluno")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description =  "Relação disciplina-aluno removida"),
//            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
//            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
//    })
//    @DeleteMapping("/aluno/{id}")
//    public void removerAluno(@PathVariable Long id){
//        alunoRepository.deleteById(id);
//    }
//
    /* (FIM) COLOCAR ISSO NO DISCIPLINA ALUNO API */


}
