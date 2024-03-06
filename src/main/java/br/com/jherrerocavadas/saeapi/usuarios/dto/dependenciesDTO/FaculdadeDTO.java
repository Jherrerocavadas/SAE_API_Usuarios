package br.com.jherrerocavadas.saeapi.usuarios.dto.dependenciesDTO;

import lombok.Data;

@Data
public class FaculdadeDTO {
    private Long id;

    private String codFaculdade;
    private String nomeFaculdade;
    private String siglaFaculdade;
    private String cidade;
    private String endereco;
}
