package br.com.pdz.auth.model.request;

import br.com.pdz.auth.model.Perfil;

import java.util.Set;
import java.util.stream.Collectors;

public class PerfilRequest {
    private String nome;

    public PerfilRequest(String nome) {
        this.nome = nome;
    }

    @Deprecated
    public PerfilRequest() {
    }

    public static Set<Perfil> converter(Set<PerfilRequest> perfilRequests) {
        return perfilRequests.stream().map(Perfil::new).collect(Collectors.toSet());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
