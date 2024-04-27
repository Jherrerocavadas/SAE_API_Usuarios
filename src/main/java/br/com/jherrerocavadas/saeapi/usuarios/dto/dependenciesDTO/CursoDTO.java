package br.com.jherrerocavadas.saeapi.usuarios.dto.dependenciesDTO;

import lombok.Data;

@Data
public class CursoDTO {

    private Long id;
    private String nomeCurso;
    private String siglaCurso;
    private Integer qtdSemestres;

}
