package br.com.jherrerocavadas.saeapi.usuarios.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numMatricula;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "numCadastroUsuario", referencedColumnName = "numUsuario",
            foreignKey = @ForeignKey(name = "UK_PROFESSOR_USUARIO", value = ConstraintMode.CONSTRAINT))

    private Usuario usuario;
}
