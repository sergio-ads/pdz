package br.com.pdz.auth.model.request;

import br.com.pdz.auth.model.Perfil;
import br.com.pdz.auth.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioRequest {

    private String email;
    private String senha;
    private Set<PerfilRequest> perfis = new HashSet<>();

    public UsuarioRequest(String email, String senha, Set<PerfilRequest> perfis) {
        this.email = email;
        this.senha = senha;
        this.perfis = perfis;
    }

    @Deprecated
    public UsuarioRequest() {
    }

    public Usuario convertWithPerfil() {
        return new Usuario(email, senha, perfis.stream().map(Perfil::new).collect(Collectors.toSet()));
    }

    public Usuario convertWithoutPerfil() {
        return new Usuario(email, senha, new HashSet<>());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<PerfilRequest> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<PerfilRequest> perfis) {
        this.perfis = perfis;
    }

}
