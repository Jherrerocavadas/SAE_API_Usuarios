package br.com.jherrerocavadas.saeapi.usuarios.dto;

import br.com.jherrerocavadas.saeapi.usuarios.dto.dependenciesDTO.CursoDTO;
import br.com.jherrerocavadas.saeapi.usuarios.dto.dependenciesDTO.FaculdadeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlunoResponseDTO {

    private Long numMatricula;

    private Float percentualProgressao;

    private Float percentualRendimento;

    private Integer semestre;

    private UsuarioDTO usuario;

    private FaculdadeDTO faculdade;

    private CursoDTO curso;
}
