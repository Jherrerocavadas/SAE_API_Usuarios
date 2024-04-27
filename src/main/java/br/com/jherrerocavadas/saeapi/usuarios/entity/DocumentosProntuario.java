package br.com.jherrerocavadas.saeapi.usuarios.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DocumentosProntuario {
    /* DOCUMENTOS QUE SERÃO ANEXADOS NOS PRONTUÁRIOS DOS ALUNOS E PROFESSORES */

    /*
    * ID
    * TIPO DOCUMENTO
    * NOME DOCUMENTO
    * BASE64/BLOB DOCUMENTO
    * EXTENSÃO DOCUMENTO
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoDocumento; //OK: Colocar tipo de documento
    private String nomeDocumento;
    private String documento;
    private String extensaoDocumento;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "numUsuario", referencedColumnName = "numUsuario",
//            insertable=false, updatable=false,
            foreignKey = @ForeignKey(name = "UK_USUARIO_DOCUMENTO_PRONTUARIO", value = ConstraintMode.CONSTRAINT))
    private Usuario usuario;

//    private String tipoUsuario;

}
