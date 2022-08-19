package br.com.pdz.cadastro.controller;

import br.com.pdz.cadastro.model.Usuario;
import br.com.pdz.cadastro.model.request.UsuarioRequest;
import br.com.pdz.cadastro.model.response.UsuarioResponse;
import br.com.pdz.cadastro.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/usuario")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(UsuarioResponse.converter(usuarios));
    }

    @GetMapping("/usuario/{email}")
    @PreAuthorize("hasAuthority('ADMIN') || #email == authentication.principal.username")
    public ResponseEntity<UsuarioResponse> getUsuario(@PathVariable String email) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(email);

        return optionalUsuario.map(usuario -> ResponseEntity.ok(new UsuarioResponse(usuario)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/usuario", method = {RequestMethod.POST, RequestMethod.PUT})
    @PreAuthorize("hasAuthority('ADMIN') || #usuarioRequest.email == authentication.principal.username")
    public ResponseEntity<UsuarioResponse> alterarUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioRepository.findById(usuarioRequest.getEmail())
                .orElse(usuarioRequest.converter());
        usuarioRepository.save(usuarioRequest.atualizar(usuario));

        return ResponseEntity.ok(new UsuarioResponse(usuario));
    }

}
