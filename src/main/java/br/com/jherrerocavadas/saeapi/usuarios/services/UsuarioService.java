package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.dto.UsuarioDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import br.com.jherrerocavadas.saeapi.usuarios.enums.TipoUsuario;
import br.com.jherrerocavadas.saeapi.usuarios.repository.AlunoRepository;
import br.com.jherrerocavadas.saeapi.usuarios.repository.ProfessorRepository;
import br.com.jherrerocavadas.saeapi.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;


    @Autowired
    private UsuarioService(UsuarioRepository usuarioRepository,
                       AlunoRepository alunoRepository,
                       ProfessorRepository professorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
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

    public UsuarioDTO usuarioToUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setLogin(usuario.getLogin());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setNome(usuario.getNome());
//        usuarioDTO.setNumMatricula(usuario.getNumMatricula());
        usuarioDTO.setNumUsuario(usuario.getNumUsuario());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setTipoUsuario(TipoUsuario.tipoUsuarioByCodUsuario(usuario.getTipoUsuario()));

        return usuarioDTO;

    }

    public Boolean autenticar(UsuarioDTO usuarioDTO) {

        Usuario usuario = usuarioRepository.findByLoginAndSenha(usuarioDTO.getLogin(), usuarioDTO.getSenha());

        return Objects.nonNull(usuario);



    }

    public Usuario usuarioDtoToUsuario(UsuarioDTO usuarioDTO) {

        Usuario usuario = new Usuario();
        usuario.setLogin(usuarioDTO.getLogin());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setNumUsuario(usuarioDTO.getNumUsuario());
//        usuario.setNumMatricula(usuarioDTO.getNumMatricula());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setTipoUsuario(usuarioDTO.getTipoUsuario().getCodTipo());

        return usuario;
    }

    public Usuario findUsuarioByTipoUsuarioAndLogin(String tipoUsuario, String login) {

       TipoUsuario tipoUsuarioEnum = TipoUsuario.codUsuarioByTipoUsuario(tipoUsuario);

        return usuarioRepository.findByTipoUsuarioAndLogin(tipoUsuarioEnum.getCodTipo(), login);



    }

    public Optional<Usuario> findUsuarioByNumUsuario(Long numUsuario) {

        return usuarioRepository.findById(numUsuario);
    }
    public String inserirFotoUsuario(MultipartFile fotoUsuario, Long numUsuario) {
        Optional<Usuario> usuarioOptional = this.findUsuarioByNumUsuario(numUsuario);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();

            try {
                usuario.setFotoUsuario(fotoUsuario.getBytes());
                usuarioRepository.save(usuario);
                return usuario.getFotoUsuario().toString();
            } catch (IOException e) {
                throw new RuntimeException("Erro ao fazer o encode do base64 da foto do usuário");
            }

        }
        return "Sem foto, amigo";
    }
}
