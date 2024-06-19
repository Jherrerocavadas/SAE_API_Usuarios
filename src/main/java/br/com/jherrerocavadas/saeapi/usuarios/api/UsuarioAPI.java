package br.com.jherrerocavadas.saeapi.usuarios.api;

import br.com.jherrerocavadas.saeapi.usuarios.dto.UsuarioDTO;
import br.com.jherrerocavadas.saeapi.usuarios.dto.requests.LoginUsuarioRequestDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import br.com.jherrerocavadas.saeapi.usuarios.repository.UsuarioRepository;
import br.com.jherrerocavadas.saeapi.usuarios.services.AlunoService;
import br.com.jherrerocavadas.saeapi.usuarios.services.ProfessorService;
import br.com.jherrerocavadas.saeapi.usuarios.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@Tag(name = "Usuario", description = "Endpoints relacionados à ações de um usuário no sistema")
public class UsuarioAPI {

    private final String SYSTEM_HEADER = "X-System-Cod";


    private final UsuarioRepository usuarioRepository;
    private final AlunoService alunoService;
    private final UsuarioService usuarioService;


    @Autowired
    private UsuarioAPI(UsuarioRepository usuarioRepository,
                       AlunoService alunoService,
                       UsuarioService usuarioService){
        this.usuarioRepository = usuarioRepository;
        this.alunoService = alunoService;
        this.usuarioService = usuarioService;

    }


    @Operation(summary =  "Retornar todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Lista retornada"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/usuarios")
    //Tipo de usuário?
    public List<UsuarioDTO> retornarUsuarios(){
        return usuarioService.findAllUsuariosDTO();
    }



    @Operation(summary =  "Retornar os dados de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Dados Retornados"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados", content = {}),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/usuarios/{tipoUsuario}/{username}")
    public ResponseEntity<Object> retornarUsuarioPorTipoELogin(@PathVariable String tipoUsuario,
                                                               @PathVariable String username){

        Usuario usuario = usuarioService.findUsuarioByTipoUsuarioAndUsername(tipoUsuario, username);

        if(Objects.nonNull(usuario)){
        return ResponseEntity.ok(usuarioService.usuarioToUsuarioDTO(usuario));
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary =  "Autenticar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description =  "Usuário autenticado"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados", content = {}),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/usuarios/autenticar")
    public ResponseEntity<Object> autenticarUsuario(@RequestHeader(SYSTEM_HEADER) String system,
                                                    @RequestBody LoginUsuarioRequestDTO loginUsuarioRequestDTO){

        return ResponseEntity.ok(usuarioService.autenticarUsuario(loginUsuarioRequestDTO));
    }

    @Operation(summary =  "Autenticar um aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description =  "Usuário autenticado"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados", content = {}),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/usuarios/autenticar/aluno")
    public ResponseEntity<Object> autenticarAluno(@RequestHeader(SYSTEM_HEADER) String system,
                                                  @RequestBody LoginUsuarioRequestDTO loginUsuarioRequestDTO){

        return ResponseEntity.ok(alunoService.autenticarAluno(loginUsuarioRequestDTO));
    }


    @Operation(summary =  "Cadastrar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Usuário cadastrado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/usuario")
    public void salvarUsuario(@RequestBody UsuarioDTO usuarioDTO){

        usuarioService.salvarUsuario(usuarioDTO);

    }


    @Operation(summary =  "Enviar uma foto de perfil do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Foto enviada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping(path = "/usuario/{numUsuario}/foto",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> enviarFoto(@PathVariable("numUsuario") Long numUsuario,
                                             @RequestParam("fotoUsuario") MultipartFile fotoUsuario){

        return ResponseEntity.ok(usuarioService.inserirFotoUsuario(fotoUsuario, numUsuario));

    }


    @Operation(summary =  "Editar uma usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Relação usuário alterado e salvo"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PutMapping("/usuario/{numUsuario}")
    public void editarUsuario(@RequestBody UsuarioDTO usuarioDTO, @PathVariable Long numUsuario){
        Usuario usuario = usuarioService.usuarioDtoToUsuario(usuarioDTO);

        usuario.setNumUsuario(numUsuario);
        usuarioRepository.save(usuario);
    }


    @Operation(summary =  "Remover um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Usuario removido"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/usuario/{numUsuario}")
    public void removerUsuario(@PathVariable Long numUsuario){
        usuarioRepository.deleteById(numUsuario);
    }

}
