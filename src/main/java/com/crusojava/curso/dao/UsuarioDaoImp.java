package com.crusojava.curso.dao;


import com.crusojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public List<Usuario> getUsuarios() {

        String query = "FROM Usuario";
        List <Usuario> resultado = entityManager.createQuery(query).getResultList();
        return resultado;
    }

    @Override
    public void eliminar(Long id) {

        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {

        entityManager.merge(usuario);

    }

    @Override
    public Usuario verificarCredenciales(Usuario usuario){

        String query = "FROM Usuario WHERE email = :email";
        List <Usuario> resultado = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if (resultado.isEmpty()){

            return null;

        }

        String passwordHashed = resultado.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);


        if(argon2.verify(passwordHashed, usuario.getPassword().toCharArray())){

            return resultado.get(0);

        }

        return null;

    }

}
