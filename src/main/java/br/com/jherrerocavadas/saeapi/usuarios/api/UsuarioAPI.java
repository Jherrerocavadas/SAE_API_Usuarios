package br.com.jherrerocavadas.saeapi.usuarios.api;

import br.com.jherrerocavadas.saeapi.usuarios.dto.UsuarioDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import br.com.jherrerocavadas.saeapi.usuarios.repository.AlunoRepository;
import br.com.jherrerocavadas.saeapi.usuarios.repository.ProfessorRepository;
import br.com.jherrerocavadas.saeapi.usuarios.repository.UsuarioRepository;
import br.com.jherrerocavadas.saeapi.usuarios.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class UsuarioAPI {


    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final UsuarioService usuarioService;


    @Autowired
    private UsuarioAPI(UsuarioRepository usuarioRepository,
                       AlunoRepository alunoRepository,
                       ProfessorRepository professorRepository,
                       UsuarioService usuarioService){
        this.usuarioRepository = usuarioRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.usuarioService = usuarioService;

    }

    @Operation(summary =  "Verificar o serviço de usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Serviço OK"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/check-usuario-service")
    public ResponseEntity<String> verificaServico(){
        return ResponseEntity.ok().body("Serviço operacional");
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
    @GetMapping("/usuarios/{tipoUsuario}/{login}")
    public ResponseEntity<UsuarioDTO> retornarUsuarioPorTipoELogin(@PathVariable String tipoUsuario,
                                                                   @PathVariable String login){

        Usuario usuario = usuarioService.findUsuarioByTipoUsuarioAndLogin(tipoUsuario, login);

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
    public ResponseEntity<Boolean> retornarUsuarioPorTipoELogin(@RequestBody UsuarioDTO usuarioDTO){

        return ResponseEntity.accepted().body(usuarioService.autenticar(usuarioDTO));
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

        usuarioRepository.save(usuarioService.usuarioDtoToUsuario(usuarioDTO));
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