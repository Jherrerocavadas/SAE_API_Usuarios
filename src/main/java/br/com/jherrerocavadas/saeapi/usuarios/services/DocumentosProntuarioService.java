package br.com.jherrerocavadas.saeapi.usuarios.services;

import br.com.jherrerocavadas.saeapi.usuarios.entity.DocumentosProntuario;
import br.com.jherrerocavadas.saeapi.usuarios.repository.DocumentosProntuarioRepository;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;

@Service
public class DocumentosProntuarioService {

    private final DocumentosProntuarioRepository documentosProntuarioRepository;

    public DocumentosProntuarioService(DocumentosProntuarioRepository documentosProntuarioRepository) {
        this.documentosProntuarioRepository = documentosProntuarioRepository;
    }


    public DocumentosProntuario documentoToDocumentoProntuario(MultipartFile arquivo){
        DocumentosProntuario documentoProntuario = new DocumentosProntuario();

        String filename = arquivo.getOriginalFilename();

        documentoProntuario.setNomeDocumento(filename);
        //Busca o último ponto e gera uma string dali pra frente, que corresponde à extensão do arquivo
        documentoProntuario.setExtensaoDocumento(filename.substring(filename.lastIndexOf(".")));
        documentoProntuario.setTipoDocumento(arquivo.getContentType()); // -> tipo do documento, mapear por string talvez


        try {
            String arquivoBase64 = Base64.getEncoder().encodeToString(arquivo.getBytes());
            documentoProntuario.setDocumento(arquivoBase64);
        } catch (Exception e) {
            throw new RuntimeException("Exception, baby! look: ", e);
        }

        return documentoProntuario;

    }
    public ResponseEntity<?> baixarDocumento(Long id) {
        Optional<DocumentosProntuario> optionalDocumentosProntuario = documentosProntuarioRepository.findById(id);
        if(optionalDocumentosProntuario.isPresent()){
            DocumentosProntuario documentoProntuario = optionalDocumentosProntuario.get();
            HttpHeaders headers = new HttpHeaders();
            byte[] arquivoBinario = Base64.getDecoder().decode(documentoProntuario.getDocumento());
            InputStream is = new ByteArrayInputStream(arquivoBinario);
            InputStreamResource resource = new InputStreamResource(is);
            headers.add(HttpHeaders.CONTENT_TYPE, documentoProntuario.getTipoDocumento());
            headers.setContentDisposition(ContentDisposition.attachment().filename(documentoProntuario.getNomeDocumento()).build());
            return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
        }
        return ResponseEntity.status(404).body("Não foi encontrado o arquivo com o id informado.");

    }

}
