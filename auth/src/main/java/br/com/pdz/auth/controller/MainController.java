package br.com.pdz.auth.controller;

import br.com.pdz.auth.model.Perfil;
import br.com.pdz.auth.model.Usuario;
import br.com.pdz.auth.model.request.LoginRequest;
import br.com.pdz.auth.model.request.PerfilRequest;
import br.com.pdz.auth.model.request.UsuarioRequest;
import br.com.pdz.auth.model.response.LoginResponse;
import br.com.pdz.auth.model.response.UsuarioResponse;
import br.com.pdz.auth.repository.PerfilRepository;
import br.com.pdz.auth.repository.UsuarioRepository;
import br.com.pdz.auth.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class MainController {
    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public MainController(AuthenticationManager authManager, TokenService tokenService, UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/auth")
    public ResponseEntity<LoginResponse> autenticar(@RequestBody @Validated LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken dadosLogin = loginRequest.converter();

        try {
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new LoginResponse("Bearer", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/usuario")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios.stream().map(UsuarioResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/usuario/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioResponse> getUsuario(@PathVariable String email) {
        try {
            Usuario usuario = usuarioRepository.findById(email).get();
            return ResponseEntity.ok(new UsuarioResponse(usuario));
        } catch (AuthenticationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/usuario")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioResponse> alterarUsuario(@RequestBody @Validated UsuarioRequest usuarioRequest) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioRequest.getEmail())
                    .orElse(usuarioRequest.convertWithPerfil());
            Set<Perfil> perfis = PerfilRequest.converter(usuarioRequest.getPerfis());

            usuario.setPerfis(perfis);
            perfilRepository.saveAll(perfis);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok(new UsuarioResponse(usuario));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/usuario")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> salvarUsuario(@RequestBody @Validated UsuarioRequest usuarioRequest) {
        try {
            Optional<Usuario> usuarioProvavel = usuarioRepository.findById(usuarioRequest.getEmail());
            Perfil perfilUser = perfilRepository.findById("USER").get();

            if(usuarioProvavel.isEmpty()) {
                Usuario usuario = usuarioRequest.convertWithoutPerfil();
                usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
                usuario.getPerfis().add(perfilUser);
                usuarioRepository.save(usuario);
                return ResponseEntity.ok(new UsuarioResponse(usuario));
            } else {
                return ResponseEntity.unprocessableEntity().body("{\"error\": \"Usuário já cadastrado\"}");
            }

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/usuario/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> apagarUsuario(@PathVariable String email) {
        try {
            Optional<Usuario> usuarioProvavel = usuarioRepository.findById(email);

            if(usuarioProvavel.isPresent()) {
                usuarioRepository.delete(usuarioProvavel.get());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
