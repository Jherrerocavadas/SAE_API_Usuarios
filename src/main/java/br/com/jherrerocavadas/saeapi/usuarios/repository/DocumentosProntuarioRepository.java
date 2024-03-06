package br.com.jherrerocavadas.saeapi.usuarios.repository;

import br.com.jherrerocavadas.saeapi.usuarios.entity.DocumentosProntuario;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DocumentosProntuarioRepository extends JpaRepository<DocumentosProntuario, Long> {


    // a condição IS NULL é para permitir que essa variável seja ignorada na query se o parâmetro for nulo
    // :#{#NOME_PARAMETRO} foi definido assim pois o spring-data tem um bug que não processa apenas o :NOME_PARAMETRO
    // se o parâmetro for nulo
    @Query("SELECT DP FROM DocumentosProntuario DP " +
            "INNER JOIN Usuario USR ON DP.usuario.numUsuario = USR.numUsuario " +
            "WHERE(:#{#extensaoDocumento} IS NULL OR DP.extensaoDocumento LIKE %:extensaoDocumento%) " +
            "AND(:#{#nomeDocumento} IS NULL OR DP.nomeDocumento LIKE %:nomeDocumento%) " +
            "AND(:#{#nome} IS NULL OR USR.nome LIKE %:nomeUsuario%) ")
    List<DocumentosProntuario> findAllByFilters(@Param("extensaoDocumento") String extensaoDocumento,
                                                @Param("nomeDocumento") String nomeDocumento,
                                                @Param("nomeUsuario") String nomeUsuario);

    List<DocumentosProntuario> findAllByUsuarioNumUsuario(Long numMatriculaUsuario);
}
