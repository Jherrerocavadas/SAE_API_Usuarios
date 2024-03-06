package br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies;

import br.com.jherrerocavadas.saeapi.usuarios.enums.Periodo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HorarioAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Periodo periodo;
    private Integer numeroAula;
    private String inicioAula;
    private String fimAula;
    private Boolean isIntervalo;

    public HorarioAula(Long id) {
        this.id = id;
    }
}
