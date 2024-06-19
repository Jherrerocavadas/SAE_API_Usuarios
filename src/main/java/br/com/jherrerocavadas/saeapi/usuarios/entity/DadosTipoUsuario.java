package br.com.jherrerocavadas.saeapi.usuarios.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DadosTipoUsuario {


    @Id
    private Integer codTipo;
    private String nomeTipo;
    private String codTipoString;
    private String secretKey;
    private String tipoUsuario;



}

