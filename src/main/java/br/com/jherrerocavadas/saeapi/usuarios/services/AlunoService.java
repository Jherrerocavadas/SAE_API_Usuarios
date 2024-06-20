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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoService {

    private AlunoRepository alunoRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public AlunoService(AlunoRepository alunoRepository,
                        UsuarioService usuarioService) {
        this.alunoRepository = alunoRepository;
        this.usuarioService = usuarioService;
    }

    //Converter DTO para entidade do banco de dados
    public Aluno alunoDtoToAluno(AlunoDTO alunoDTO) {
        return Aluno.builder()
                .numMatricula(alunoDTO.getNumMatricula())
                .semestre(alunoDTO.getSemestre())
                .percentualProgressao(alunoDTO.getPercentualProgressao())
                .percentualRendimento(alunoDTO.getPercentualRendimento())
                .faculdade(new Faculdade(alunoDTO.getFaculdadeId()))
                .curso(new Curso(alunoDTO.getCursoId()))
                .build();
    }


    //TODO: CRIAR PACOTE DE CONVERTERS
    /*----------------------------------------------- < DtoToEntity > ------------------------------------------------*/
    public AlunoDTO alunoToAlunoDto(Aluno aluno){
        return AlunoDTO.builder()
                .numMatricula(aluno.getNumMatricula())
                .semestre(aluno.getSemestre())
                .percentualProgressao(aluno.getPercentualProgressao())
                .percentualRendimento(aluno.getPercentualRendimento())
                .faculdadeId(aluno.getFaculdade().getId())
                .cursoId(aluno.getCurso().getId())
                .build();
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
