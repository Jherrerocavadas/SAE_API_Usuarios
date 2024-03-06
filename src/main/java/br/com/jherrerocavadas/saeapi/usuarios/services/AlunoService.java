package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.dto.AlunoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Aluno;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies.Faculdade;
import br.com.jherrerocavadas.saeapi.usuarios.services.dependencies.FaculdadeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoService {
    private FaculdadeService faculdadeService;

    private UsuarioService usuarioService;

    public AlunoService(FaculdadeService faculdadeService,
                        UsuarioService usuarioService) {
        this.faculdadeService = faculdadeService;
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
        return alunoDTO;
    }

    public Aluno relacionarAlunoPorNumUsuario(Long numUsuario, Aluno aluno) {

        Optional<Usuario> usuarioOptional = usuarioService.findUsuarioByNumUsuario(numUsuario);

        if(usuarioOptional.isPresent()){
            aluno.setUsuario(usuarioOptional.get());
        }

        return aluno;
    }
}
