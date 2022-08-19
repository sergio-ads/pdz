package br.com.pdz.auth.model;

import br.com.pdz.auth.model.request.PerfilRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Document
public class Perfil implements GrantedAuthority {
    @Id
    private String nome;

    @Deprecated
    public Perfil() {
    }

    public Perfil(String nome) {
        this.nome = nome;
    }

    public Perfil(PerfilRequest perfilRequest) {
        this.nome = perfilRequest.getNome();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getAuthority() {
        return this.nome;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "nome='" + nome + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil = (Perfil) o;
        return nome.equals(perfil.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

}
