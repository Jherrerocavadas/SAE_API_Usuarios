package br.com.jherrerocavadas.saeapi.usuarios.dto;
import org.springframework.web.multipart.MultipartFile;


public class DocumentosProntuarioDTO {
    /* DOCUMENTOS QUE SERÃO ANEXADOS NOS PRONTUÁRIOS DOS ALUNOS E PROFESSORES */

    /*
     * ID
     * TIPO DOCUMENTO
     * NOME DOCUMENTO
     * BASE64/BLOB DOCUMENTO
     * EXTENSÃO DOCUMENTO
     * */

    private Long tipoDocumento;
    private String nomeDocumento;
    private MultipartFile documento;
    private String extensaoDocumento;

}
