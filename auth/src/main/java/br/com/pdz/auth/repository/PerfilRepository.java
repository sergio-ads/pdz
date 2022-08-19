package br.com.pdz.auth.repository;

import br.com.pdz.auth.model.Perfil;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends MongoRepository<Perfil, String> {

}
