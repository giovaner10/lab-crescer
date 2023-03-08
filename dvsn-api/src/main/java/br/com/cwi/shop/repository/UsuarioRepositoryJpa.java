package br.com.cwi.shop.repository;

import br.com.cwi.shop.entities.Comentario;
import br.com.cwi.shop.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UsuarioRepositoryJpa extends CrudRepository<Comentario, Long> {

    @Query("SELECT u FROM  Usuario u WHERE LOWER(u.nome) LIKE %:nome%")
    List<Usuario> findAllUsersByName(String nome);

}
