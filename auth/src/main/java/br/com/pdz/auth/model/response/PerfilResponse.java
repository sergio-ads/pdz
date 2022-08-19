package br.com.pdz.auth.model.response;

import br.com.pdz.auth.model.Perfil;

public class PerfilResponse {
    private String nome;

    public PerfilResponse(Perfil perfil) {
        this.nome = perfil.getNome();
    }

    public String getNome() {
        return nome;
    }

}
