package br.com.pdz.cadastro.model.request;

import br.com.pdz.cadastro.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class UsuarioRequest {
    @NotBlank @Email
    private String email;
    @NotBlank
    private String nome;
    @NotNull @Past
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate nascimento;
    @NotBlank
    @Pattern(regexp = "^[M|F]$")
    private String sexo;

    @Deprecated
    public UsuarioRequest() {
    }

    public Usuario converter() {
        return new Usuario(email, nome, nascimento, sexo, null);
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

    public Usuario atualizar(Usuario usuario) {
        usuario.setNome(this.nome);
        usuario.setNascimento(this.nascimento);
        usuario.setSexo(this.sexo);

        return usuario;
    }

}
