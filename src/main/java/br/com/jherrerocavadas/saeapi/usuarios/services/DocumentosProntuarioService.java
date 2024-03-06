package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.entity.DocumentosProntuario;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import br.com.jherrerocavadas.saeapi.usuarios.repository.UsuarioRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.util.Base64;

@Service
public class DocumentosProntuarioService {



    public DocumentosProntuario documentoToDocumentoProntuario(MultipartFile arquivo){
        DocumentosProntuario documentoProntuario = new DocumentosProntuario();

        String filename = arquivo.getOriginalFilename();

        documentoProntuario.setNomeDocumento(filename);
        //Busca o último ponto e gera uma string dali pra frente, que corresponde à extensão do arquivo
        documentoProntuario.setExtensaoDocumento(filename.substring(filename.lastIndexOf(".")));
        documentoProntuario.setTipoDocumento(arquivo.getContentType()); // -> tipo do documento, mapear por string talvez


        try {
//            String arquivoBase64 = Base64.getMimeEncoder().encodeToString(arquivo.getBytes());
            documentoProntuario.setDocumento(arquivo.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Exception, baby! look: ", e);
        }

        return documentoProntuario;




    }

}
