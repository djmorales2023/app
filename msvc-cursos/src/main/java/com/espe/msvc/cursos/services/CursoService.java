package com.espe.msvc.cursos.services;


import com.espe.msvc.cursos.models.Usuario;
import com.espe.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listar();

    Optional<Curso> porId(Long id);

    Curso guardar(Curso curso);

    void eliminar(Long id);

    Optional<Usuario> agregarUsuario(Usuario usuario, Long idCurso);
    Optional<Usuario> crearUsuario(Usuario usuario, Long idCurso);
    //El método eliminarUsuario elimina al usuario del curso.
    Optional<Usuario> quitarUsuario(Usuario usuario, Long idCurso);

    Optional<Curso> listarUsuarioPorCurso(Long id);

}
