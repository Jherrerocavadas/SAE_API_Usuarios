package br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Faculdade {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codFaculdade;
    private String nomeFaculdade;
    private String siglaFaculdade;
    private String cidade;
    private String endereco;

    public Faculdade(Long id) {
        this.id = id;
        }
    }
