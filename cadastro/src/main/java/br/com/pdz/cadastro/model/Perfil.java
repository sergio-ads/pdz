package br.com.pdz.cadastro.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public class Perfil implements GrantedAuthority {
    private String nome;

    @Deprecated
    public Perfil() { }

    public Perfil(String nome) {
        this.nome = nome;
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
