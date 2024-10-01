package com.espe.msvc.usuarios.services;

import com.espe.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();

    Optional<Usuario> buscarPorId (long id);

    Optional<Usuario> buscarPorEmail (String email);

    Usuario guardar(Usuario usuario);

    void eliminar(Long id);

    List<Usuario> listarUsuariosPorId(Iterable<Long> ids);

}
