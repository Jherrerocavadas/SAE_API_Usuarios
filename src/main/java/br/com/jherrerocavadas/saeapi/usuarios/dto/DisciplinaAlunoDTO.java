package br.com.jherrerocavadas.saeapi.usuarios.dto;

import lombok.Data;

@Data
public class DisciplinaAlunoDTO {


    private Long id;
    private Long numMatricula;
    private Long disciplinaCursoId;
    private boolean isCursada;
    private boolean isDispensada;
}
