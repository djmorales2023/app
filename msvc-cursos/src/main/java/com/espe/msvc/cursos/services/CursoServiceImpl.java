package com.espe.msvc.cursos.services;
import com.espe.msvc.cursos.clients.UsuarioClientRest;
import com.espe.msvc.cursos.models.Usuario;
import com.espe.msvc.cursos.models.entity.CursoUsuario;
import com.espe.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.espe.msvc.cursos.models.entity.Curso;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {
    @Autowired
    private CursoRepository repository;

    @Autowired
    UsuarioClientRest usuarioClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> agregarUsuario(Usuario usuario, Long idCurso) {
        //Verificar que exista el curso.
        Optional<Curso> o = repository.findById(idCurso);
        if(o.isPresent()){
            //Verificar que exista el usuario.
            Usuario usuarioMicro = usuarioClientRest.detalle(usuario.getId());

            //Agregamos el usuario al curso.
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMicro.getId());

            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMicro);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long idCurso) {
        //Verificar que exista el curso.
        Optional<Curso> o = repository.findById(idCurso);
        if(o.isPresent()){
            //Verificar que exista el usuario.
            Usuario usuarioMicro = usuarioClientRest.crear(usuario);

            //Agregamos el usuario al curso.
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMicro.getId());

            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMicro);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> quitarUsuario(Usuario usuario, Long idCurso) {
        //Verificar que exista el curso.
        Optional<Curso> o = repository.findById(idCurso);
        if(o.isPresent()){
            //Verificar que exista el usuario.
            Usuario usuarioMicro = usuarioClientRest.detalle(usuario.getId());

            //Agregamos el usuario al curso.
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMicro.getId());

            curso.removeCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMicro);
        }

        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> listarUsuarioPorCurso(Long id) {
        Optional<Curso> o = repository.findById(id);
        if (o.isPresent()){
            Curso curso = o.get();
            if(!curso.getCursoUsuarios().isEmpty()){
                List<Long> ids = curso.getCursoUsuarios().stream().map(CursoUsuario::getUsuarioId).toList();
                List<Usuario> usuarios = usuarioClientRest.listarUsuariosPorId(ids);
                curso.setCursoUsuarios(usuarios);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }
}
