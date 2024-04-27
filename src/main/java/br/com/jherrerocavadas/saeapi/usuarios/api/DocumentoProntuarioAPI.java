package br.com.jherrerocavadas.saeapi.usuarios.api;

import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import br.com.jherrerocavadas.saeapi.usuarios.repository.AlunoRepository;
import br.com.jherrerocavadas.saeapi.usuarios.repository.ProfessorRepository;
import br.com.jherrerocavadas.saeapi.usuarios.repository.UsuarioRepository;
import br.com.jherrerocavadas.saeapi.usuarios.services.DocumentosProntuarioService;
import br.com.jherrerocavadas.saeapi.usuarios.entity.DocumentosProntuario;
import br.com.jherrerocavadas.saeapi.usuarios.repository.DocumentosProntuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
public class DocumentoProntuarioAPI {


    private final DocumentosProntuarioRepository documentosProntuarioRepository;
    private final DocumentosProntuarioService documentosProntuarioService;
    private final UsuarioRepository usuarioRepository;

    private final AlunoRepository alunoRepository;

    private final ProfessorRepository professorRepository;

    @Autowired
    private DocumentoProntuarioAPI(DocumentosProntuarioRepository documentosProntuarioRepository,
                                   UsuarioRepository usuarioRepository,
                                   DocumentosProntuarioService documentosProntuarioService,
                                   AlunoRepository alunoRepository,
                                   ProfessorRepository professorRepository){
        this.documentosProntuarioRepository = documentosProntuarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.documentosProntuarioService = documentosProntuarioService;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
    }


    @Operation(summary =  "Verificar o serviço de documentos prontuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Serviço OK"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/check-documentos-prontuarios-service")
    public ResponseEntity<String> verificaServico(){
        return ResponseEntity.ok().body("Serviço operacional");
    }


    @Operation(summary =  "Enviar um documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Documento salva"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping(path = "/documentos/{tipoUsuario}/{numMatricula}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> salvarDisciplinaUsuario(
            @PathVariable("tipoUsuario") String tipoUsuario,
            @PathVariable("numMatricula") Long numMatricula, //Pode ser RA aluno ou matricula professor
            @RequestParam("arquivo") MultipartFile arquivo){

        Optional<Usuario> usuario = Optional.ofNullable(null);
        //Buscar os dados de aluno ou professor, do tipo de usuario

        if(tipoUsuario.equals("aluno")){
             usuario = alunoRepository.findUsuarioByNumMatricula(numMatricula);
        }

        else if (tipoUsuario.equals("professor")){
             usuario = professorRepository.findUsuarioByNumMatricula(numMatricula);

        }

        /* NOVA ESTRUTURA */
//        Usuario usuario = documentosProntuarioService.findUsuarioByTipoUsuarioAndNumMatricula(tipoUsuario, numMatricula);




//        Optional<Usuario> usuario = usuarioRepository.findById(numMatriculaUsuario);
        if(usuario.isEmpty()){
            return ResponseEntity.status(404).body("Usuário não encontrado para a matrícula informada");
        }

        DocumentosProntuario documentoProntuario = documentosProntuarioService.documentoToDocumentoProntuario(arquivo);
        documentoProntuario.setUsuario(usuario.get());
        log.info(String.valueOf(documentoProntuario.getUsuario()));

        documentosProntuarioRepository.save(documentoProntuario);
        return ResponseEntity.ok(String.format("Documento salvo para o %s de matrícula %s", tipoUsuario, numMatricula));
    }


    @GetMapping(path = "usuario/{matriculaUsuario}/documentos")
    public ResponseEntity<List<DocumentosProntuario>> buscarDocumentosPorUsuario(@RequestParam("matriculaUsuario") Long numMatriculaUsuario){

        List<DocumentosProntuario> documentosProntuario = documentosProntuarioRepository.findAllByUsuarioNumUsuario(numMatriculaUsuario);

        if(Objects.nonNull(documentosProntuario))
        {
            return ResponseEntity.ok(documentosProntuario);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "documentos/{tipoUsuario}/{numMatricula}")
    public ResponseEntity<List<DocumentosProntuario>> buscarDocumentosPorTipoUsuarioENumMatricula(
            @PathVariable("tipoUsuario") String tipoUsuario,
            @RequestParam("numMatricula") Long numMatricula){

        List<DocumentosProntuario> documentosProntuario = documentosProntuarioRepository.findAllByUsuarioNumUsuario(numMatricula);

        if(Objects.nonNull(documentosProntuario))
        {
            return ResponseEntity.ok(documentosProntuario);
        }

        return ResponseEntity.notFound().build();
    }




    @Operation(summary =  "Listar documentos por filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Lista de documentos retornada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/documentos")
    public List<DocumentosProntuario> listarDocumentos(@RequestParam(required = false) String extensaoDocumento,
                                                       @RequestParam(required = false) String nomeDocumento,
                                                       @RequestParam(required = false) String usuario){



        return documentosProntuarioRepository.findAllByFilters(extensaoDocumento, nomeDocumento, usuario);
    }


    @Operation(summary =  "Baixar um documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Documento baixado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/documentos/{id}")
    public ResponseEntity<?> baixarDocumento(@PathVariable Long id){
        return documentosProntuarioService.baixarDocumento(id);
    }




    @Operation(summary =  "Remover um documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Relação disciplina-aluno removida"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/documentos/{id}")
    public void removerDocumento(@PathVariable Long id){
        documentosProntuarioRepository.deleteById(id);
    }
//


}
