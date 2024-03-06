package br.com.jherrerocavadas.saeapi.usuarios.entity;

import jakarta.persistence.*;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numMatricula;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "numCadastroUsuario", referencedColumnName = "numUsuario",
            foreignKey = @ForeignKey(name = "UK_PROFESSOR_USUARIO", value = ConstraintMode.CONSTRAINT))

    private Usuario usuario;
}
