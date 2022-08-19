package br.com.pdz.cadastro.model.response;

import br.com.pdz.cadastro.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioResponse {
    private String email;
    private String nome;
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate nascimento;
    private String sexo;

    public UsuarioResponse(Usuario usuario) {
        this.email = usuario.getEmail();
        this.nome = usuario.getNome();
        this.nascimento = usuario.getNascimento();
        this.sexo = usuario.getSexo();
    }

    public static List<UsuarioResponse> converter(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioResponse::new).collect(Collectors.toList());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

}
