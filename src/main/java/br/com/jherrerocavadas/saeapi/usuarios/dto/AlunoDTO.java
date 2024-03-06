package br.com.jherrerocavadas.saeapi.usuarios.dto;

import br.com.jherrerocavadas.saeapi.usuarios.dto.dependenciesDTO.FaculdadeDTO;
import lombok.Data;

@Data
public class AlunoDTO {

    private Long numMatricula;

    private Float percentualProgressao;

    private Float percentualRendimento;

    private Integer semestre;

    private Long faculdadeId;
}
