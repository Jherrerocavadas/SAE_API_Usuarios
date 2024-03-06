package br.com.jherrerocavadas.saeapi.usuarios.repository;

import br.com.jherrerocavadas.saeapi.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String login);

    Usuario findByTipoUsuarioAndLogin(Integer tipoUsuario, String login);

    Usuario findByLoginAndSenha(String login, String senha);
}
