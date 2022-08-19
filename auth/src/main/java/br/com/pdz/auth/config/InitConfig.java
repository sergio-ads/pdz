package br.com.pdz.auth.config;

import br.com.pdz.auth.model.Perfil;
import br.com.pdz.auth.model.Usuario;
import br.com.pdz.auth.repository.PerfilRepository;
import br.com.pdz.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

@Configuration
public class InitConfig implements ApplicationRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Perfil perfilUser = new Perfil("USER");
        Perfil perfilAdmin = new Perfil("ADMIN");

        if(!perfilRepository.existsById("USER")) {
            perfilRepository.save(perfilUser);
        }
        if(!perfilRepository.existsById("ADMIN")) {
            perfilRepository.save(perfilAdmin);
        }
        if(!usuarioRepository.existsById("admin@admin.com")) {
            Usuario usuario = new Usuario("admin@admin.com", new BCryptPasswordEncoder().encode("password"), new HashSet<>());
            usuario.getPerfis().add(perfilUser);
            usuario.getPerfis().add(perfilAdmin);
            usuarioRepository.save(usuario);
        }
    }

}
