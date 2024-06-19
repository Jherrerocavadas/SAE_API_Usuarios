package br.com.jherrerocavadas.saeapi.usuarios.dto;

import br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies.Curso;
import br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies.Faculdade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DadosComplementaresAlunoDTO {

    private Long numMatricula;

    private Float percentualProgressao;

    private Float percentualRendimento;

    private Integer semestre;

    private Faculdade faculdade;

    private Curso curso;
}
