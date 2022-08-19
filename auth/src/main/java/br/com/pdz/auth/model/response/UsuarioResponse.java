package br.com.pdz.auth.model.response;

import br.com.pdz.auth.model.Usuario;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioResponse {
    private String email;
    private Set<PerfilResponse> perfis = new HashSet<>();

    public UsuarioResponse(Usuario usuario) {
        this.email = usuario.getEmail();
        this.perfis.addAll(usuario.getPerfis().stream().map(PerfilResponse::new).collect(Collectors.toSet()));
    }

    public String getEmail() {
        return email;
    }

    public Set<PerfilResponse> getPerfis() {
        return perfis;
    }

}
