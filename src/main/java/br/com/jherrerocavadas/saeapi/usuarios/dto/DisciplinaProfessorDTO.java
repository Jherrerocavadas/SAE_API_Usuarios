package br.com.jherrerocavadas.saeapi.usuarios.dto;

import lombok.Data;

@Data
public class DisciplinaProfessorDTO {


    private Long id;
    private Long numMatricula;
    private Long disciplinaCursoId;
    private Boolean isEfetivo;
}
