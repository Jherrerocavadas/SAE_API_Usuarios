package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.dto.UsuarioDTO;
import br.com.jherrerocavadas.saeapi.usuarios.dto.UsuarioLoginResponseDTO;
import br.com.jherrerocavadas.saeapi.usuarios.dto.requests.LoginUsuarioRequestDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.DadosTipoUsuario;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import br.com.jherrerocavadas.saeapi.usuarios.repository.AlunoRepository;
import br.com.jherrerocavadas.saeapi.usuarios.repository.ProfessorRepository;
import br.com.jherrerocavadas.saeapi.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final AlunoRepository alunoRepository;

    private final AlunoService alunoService;
    private final ProfessorRepository professorRepository;

    private final ProfessorService professorService;

    private final JwtService jwtService;



    @Autowired
    private UsuarioService(UsuarioRepository usuarioRepository,
                           AlunoRepository alunoRepository,
                           AlunoService alunoService,
                           ProfessorRepository professorRepository,
                           ProfessorService professorService,
                           JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.alunoRepository = alunoRepository;
        this.alunoService = alunoService;
        this.professorRepository = professorRepository;
        this.professorService = professorService;
        this.jwtService = jwtService;
    }

    public List<UsuarioDTO> findAllUsuariosDTO() {

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();

        for (Usuario usuario: usuarios) {
            //Anonimizar a senha dos usuários
            usuario.setSenha("ANONIMIZED");
            usuariosDTO.add(usuarioToUsuarioDTO(usuario));
        }

        return usuariosDTO;
    }


    /*----------------------------------------------- < EntityToDTO > ------------------------------------------------*/

    //TODO: CRIAR PACOTE DE CONVERTERS

    public Usuario usuarioDtoToUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .numUsuario(usuarioDTO.getNumUsuario())
                .nome(usuarioDTO.getNome())
                .username(usuarioDTO.getUsername())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .tipoUsuario(DadosTipoUsuario.builder()
                        .tipoUsuario(usuarioDTO.getTipoUsuario())
                        .build())
                .fotoUsuario(usuarioDTO.getFotoUsuario().getBytes())
                .build();
    }

    /*----------------------------------------------- < DtoToEntity > ------------------------------------------------*/
    public UsuarioDTO usuarioToUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
        .numUsuario(usuario.getNumUsuario())
                .nome(usuario.getNome())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .tipoUsuario(usuario.getTipoUsuario().getTipoUsuario())
                .fotoUsuario(Objects.nonNull(usuario.getFotoUsuario())? Base64.getEncoder().encodeToString(usuario.getFotoUsuario()) : null)
                .build();

    }

    /*------------------------------------------- < Ações no repository > --------------------------------------------*/


    public Usuario findUsuarioByTipoUsuarioAndUsername(String tipoUsuario, String username) {

        return usuarioRepository.findByTipoUsuarioAndUsername(
                DadosTipoUsuario.builder()
                        .tipoUsuario(tipoUsuario)
                        .build(),
                        username)
                .orElseThrow(() -> new RuntimeException("usuario não encontrado!"));

    }

    public Usuario findUsuarioByNumUsuario(Long numUsuario) {

        return usuarioRepository.findById(numUsuario)
                .orElseThrow(() -> new RuntimeException("usuario não encontrado!"));
    }

    //TODO: SALVAR SENHA PADRÃO PARA PRIMEIRO ACESSO
    public void salvarUsuario(UsuarioDTO usuarioDTO) {
        usuarioRepository.save(this.usuarioDtoToUsuario(usuarioDTO));
    }

    public String inserirFotoUsuario(MultipartFile fotoUsuario, Long numUsuario) {
        Usuario usuario = this.findUsuarioByNumUsuario(numUsuario);

        try {
            usuario.setFotoUsuario(fotoUsuario.getBytes());
            usuarioRepository.save(usuario);
            return usuario.getFotoUsuario().toString();
        } catch (IOException e) {

            //TODO: TRATAR ESSA EXCEPTION CORRETAMENTE
            throw new RuntimeException("Erro ao fazer o encode do base64 da foto do usuário");
        }

    }

    public UsuarioLoginResponseDTO autenticarUsuario(LoginUsuarioRequestDTO loginUsuarioRequestDTO) {
        Usuario usuario = usuarioRepository.findByUsernameAndSenha(
                loginUsuarioRequestDTO.getUsername(),
                loginUsuarioRequestDTO.getSenha()
        );

        return UsuarioLoginResponseDTO.builder()
                .codigoUsuario(usuario.getNumUsuario())
                .email(usuario.getEmail())
                .tipoUsuario(usuario.getTipoUsuario().getTipoUsuario())
                .fotoUsuario(usuario.getFotoUsuario() != null ? Base64.getEncoder().encodeToString(usuario.getFotoUsuario()) : null)
                .nome(usuario.getNome())
                .tokenJwt(jwtService.gerarToken(usuario))
                .build();
    }
}
