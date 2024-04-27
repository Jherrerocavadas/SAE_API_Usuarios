package br.com.jherrerocavadas.saeapi.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlunoDTO {

    private Long numMatricula;

    private Float percentualProgressao;

    private Float percentualRendimento;

    private Integer semestre;

    private Long faculdadeId;

    private Long cursoId;
}
