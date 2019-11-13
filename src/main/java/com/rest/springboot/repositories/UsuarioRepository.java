package com.rest.springboot.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.springboot.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Optional<Usuario> findByEmail(String email);
	
	List<Usuario> findByPermissoesDescricao(String permissaoDescricao);

}
