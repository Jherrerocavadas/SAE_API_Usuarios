package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.dto.AlunoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.dto.AlunoResponseDTO;
import br.com.jherrerocavadas.saeapi.usuarios.dto.UsuarioDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Aluno;
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
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final AlunoRepository alunoRepository;

    private final AlunoService alunoService;
    private final ProfessorRepository professorRepository;


    @Autowired
    private UsuarioService(UsuarioRepository usuarioRepository,
                           AlunoRepository alunoRepository,
                           AlunoService alunoService, ProfessorRepository professorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.alunoRepository = alunoRepository;
        this.alunoService = alunoService;
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

    public Aluno cadastrarAlunoPorUsuario(AlunoDTO alunoDTO, Long numUsuario){
        Aluno aluno = alunoService.alunoDtoToAluno(alunoDTO);
        Optional<Usuario> usuarioOptional = this.findUsuarioByNumUsuario(numUsuario);

        if(usuarioOptional.isPresent()){
            aluno.setUsuario(usuarioOptional.get());
        }
        aluno = alunoRepository.save(aluno); //Atualizar a entidade do aluno com o número da matrícula atribuido
        return aluno;
    }


    public Usuario autenticarUsuario(UsuarioDTO usuarioDTO) {
        System.out.printf("usuarioDTO é: %s %n", usuarioDTO);
        Usuario usuario = usuarioRepository.findByLoginAndSenha(usuarioDTO.getLogin(), usuarioDTO.getSenha());
        System.out.printf("usuario é: %s %n", usuario);
        return usuario;
    }

    public AlunoResponseDTO autenticarAluno(UsuarioDTO usuarioDTO) {
        Aluno aluno = alunoRepository.findAlunoByUsuario(this.autenticarUsuario(usuarioDTO));
        aluno.getUsuario().setSenha("");
        return alunoService.toAlunoResponse(aluno, this);


    }
    public Object autenticarProfessor(UsuarioDTO usuarioDTO) {
        //TODO: gerar autenticação do professor
        return true;
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

    public void salvarUsuario(UsuarioDTO usuarioDTO) {
        usuarioRepository.save(this.usuarioDtoToUsuario(usuarioDTO));
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
