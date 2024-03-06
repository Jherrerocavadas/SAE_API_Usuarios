package br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies;


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
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCurso;
    private String siglaCurso;
    private Integer qtdSemestres;

    public Curso(Long id) {
        this.id = id;
    }
}
