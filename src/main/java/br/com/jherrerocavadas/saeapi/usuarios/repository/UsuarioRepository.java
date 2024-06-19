package br.com.jherrerocavadas.saeapi.usuarios.repository;

import br.com.jherrerocavadas.saeapi.usuarios.entity.DadosTipoUsuario;
import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
//    Usuario findByLogin(String login);

    Usuario findByTipoUsuarioAndUsername(DadosTipoUsuario tipoUsuario, String username);

    Usuario findByUsernameAndSenha(String username, String senha);
}
