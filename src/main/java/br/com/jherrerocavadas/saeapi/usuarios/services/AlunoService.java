package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.dto.AlunoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.dto.AlunoResponseDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Aluno;
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

    @Autowired
    public AlunoService(FaculdadeService faculdadeService,
                        CursoService cursoService,
                        AlunoRepository alunoRepository) {
        this.faculdadeService = faculdadeService;
        this.cursoService = cursoService;
        this.alunoRepository = alunoRepository;
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

    public AlunoResponseDTO toAlunoResponse(Aluno aluno, UsuarioService usuarioService){
        AlunoResponseDTO alunoResponseDTO = new AlunoResponseDTO();

        alunoResponseDTO.setNumMatricula(aluno.getNumMatricula());
        alunoResponseDTO.setSemestre(aluno.getSemestre());
        alunoResponseDTO.setPercentualProgressao(aluno.getPercentualProgressao());
        alunoResponseDTO.setPercentualRendimento(aluno.getPercentualRendimento());
        alunoResponseDTO.setFaculdade(faculdadeService.faculdadeToFaculdadeDto(aluno.getFaculdade()));
        alunoResponseDTO.setCurso(cursoService.cursoToCursoDto(aluno.getCurso()));
        alunoResponseDTO.setUsuario(usuarioService.usuarioToUsuarioDTO(aluno.getUsuario()));
        return alunoResponseDTO;
    }

        Optional<Usuario> usuarioOptional = usuarioService.findUsuarioByNumUsuario(numUsuario);

        if(usuarioOptional.isPresent()){
            aluno.setUsuario(usuarioOptional.get());
        }

        return aluno;
    }
}
