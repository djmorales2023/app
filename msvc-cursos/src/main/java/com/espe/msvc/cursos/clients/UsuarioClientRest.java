package com.espe.msvc.cursos.clients;

import com.espe.msvc.cursos.models.Usuario;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @GetMapping("/usuarios-por-curso")
    List<Usuario> listarUsuariosPorId(@RequestParam Iterable<Long> ids);

    @PostMapping
    Usuario crear(@RequestBody Usuario usuario);

}
