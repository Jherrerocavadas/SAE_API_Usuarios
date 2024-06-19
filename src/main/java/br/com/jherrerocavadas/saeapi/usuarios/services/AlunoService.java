package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.dto.AlunoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.dto.DadosComplementaresAlunoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.dto.UsuarioLoginResponseDTO;
import br.com.jherrerocavadas.saeapi.usuarios.dto.requests.LoginUsuarioRequestDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Aluno;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies.Curso;
import br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies.Faculdade;
import br.com.jherrerocavadas.saeapi.usuarios.repository.AlunoRepository;
import br.com.jherrerocavadas.saeapi.usuarios.services.dependencies.CursoService;
import br.com.jherrerocavadas.saeapi.usuarios.services.dependencies.FaculdadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoService {
    private FaculdadeService faculdadeService;

    private AlunoRepository alunoRepository;

    private CursoService cursoService;

    private final UsuarioService usuarioService;

    @Autowired
    public AlunoService(FaculdadeService faculdadeService,
                        CursoService cursoService,
                        AlunoRepository alunoRepository,
                        UsuarioService usuarioService) {
        this.faculdadeService = faculdadeService;
        this.cursoService = cursoService;
        this.alunoRepository = alunoRepository;
        this.usuarioService = usuarioService;
    }

    //Converter DTO para entidade do banco de dados
    public Aluno alunoDtoToAluno(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();

        aluno.setNumMatricula(alunoDTO.getNumMatricula());
        aluno.setSemestre(alunoDTO.getSemestre());
        aluno.setPercentualProgressao(alunoDTO.getPercentualProgressao());
        aluno.setPercentualRendimento(alunoDTO.getPercentualRendimento());

        aluno.setFaculdade(new Faculdade(alunoDTO.getFaculdadeId()));
        aluno.setCurso(new Curso(alunoDTO.getCursoId()));
        return aluno;
    }


    // Converter entidade do banco de dados para DTO
    public AlunoDTO alunoToAlunoDto(Aluno aluno){
        AlunoDTO alunoDTO = new AlunoDTO();

        alunoDTO.setNumMatricula(aluno.getNumMatricula());
        alunoDTO.setSemestre(aluno.getSemestre());
        alunoDTO.setPercentualProgressao(aluno.getPercentualProgressao());
        alunoDTO.setPercentualRendimento(aluno.getPercentualRendimento());

        alunoDTO.setFaculdadeId(faculdadeService.faculdadeToFaculdadeDto(aluno.getFaculdade()).getId());
        alunoDTO.setCursoId(cursoService.cursoToCursoDto(aluno.getCurso()).getId());
        return alunoDTO;
    }

    public DadosComplementaresAlunoDTO getDadosComplementaresAluno(Aluno aluno){
        return DadosComplementaresAlunoDTO.builder()
                .numMatricula(aluno.getNumMatricula())
                .semestre(aluno.getSemestre())
                .percentualProgressao(aluno.getPercentualProgressao())
                .percentualRendimento(aluno.getPercentualRendimento())
                .faculdade(aluno.getFaculdade())
                .curso(aluno.getCurso())
                .build();
    }



    public Aluno findById(Long numMatricula) {

        Optional<Aluno> alunoOptional = alunoRepository.findById(numMatricula);

        if(alunoOptional.isEmpty()){
            return null;

        }
        return alunoOptional.get();

    }

    public UsuarioLoginResponseDTO autenticarAluno(LoginUsuarioRequestDTO loginUsuarioRequestDTO) {
        UsuarioLoginResponseDTO loginResponseDTO = usuarioService.autenticarUsuario(loginUsuarioRequestDTO);
        Aluno aluno = alunoRepository.findAlunoByUsuario(Usuario.builder().numUsuario(loginResponseDTO.getCodigoUsuario()).build());
        loginResponseDTO.setDadosComplementares(this.getDadosComplementaresAluno(aluno));

        return loginResponseDTO;


    }
}
