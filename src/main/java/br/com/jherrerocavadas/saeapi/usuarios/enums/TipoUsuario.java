package br.com.jherrerocavadas.saeapi.usuarios.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//@Entity //TODO: -> Puxar os tipos dinamicamente do banco de dados, assim que rodar o tipo de enum
public enum TipoUsuario {


    ALUNO(0, "Aluno", "aluno"),
    PROFESSOR(1, "Professor", "professor"),

    SECRETARIA(2, "Secretaria", "secretaria");

//TODO: -> Puxar os tipos dinamicamente do banco de dados, assim que rodar o tipo de enum
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codTipo;
    private String nomeTipo;
    private String codTipoString;

    TipoUsuario(Integer codTipo, String nomeTipo, String codTipoString) {
        this.codTipo = codTipo;
        this.nomeTipo = nomeTipo;
        this.codTipoString = codTipoString;

    }

    public static TipoUsuario tipoUsuarioByCodUsuario(Integer codTipoUsuario) {

        for (TipoUsuario tipoUsuarioEnum: TipoUsuario.values()) {
            if(tipoUsuarioEnum.getCodTipo() == codTipoUsuario){
                return tipoUsuarioEnum;
            }

        }

        return null;
    }

    public static TipoUsuario codUsuarioByTipoUsuario(String codTipoString) {

        for (TipoUsuario tipoUsuarioEnum: TipoUsuario.values()) {
            if(tipoUsuarioEnum.getCodTipoString() == codTipoString){
                return tipoUsuarioEnum;
            }

        }

        return null;

    }


    public Integer getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(Integer codTipo) {
        this.codTipo = codTipo;
    }

    public String getNomeTipo() {
        return nomeTipo;
    }

    public void setNomeTipo(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }

    public String getCodTipoString() {
        return codTipoString;
    }

    public void setCodTipoString(String codTipoString) {
        this.codTipoString = codTipoString;
    }
}
