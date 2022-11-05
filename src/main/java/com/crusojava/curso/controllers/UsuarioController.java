package com.crusojava.curso.controllers;

import com.crusojava.curso.dao.UsuarioDao;
import com.crusojava.curso.models.Usuario;
import com.crusojava.curso.utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private Jwt jwt;

    private boolean validarToken(String token) {

        String userId = jwt.getKey(token);
        return userId != null;

    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios (@RequestHeader(value = "Authorization") String token){

        if(! validarToken(token)){
            return null;
        }

        return usuarioDao.getUsuarios();

    }



    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario (@RequestBody Usuario usuario){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword().toCharArray());
        usuario.setPassword(hash);

        usuarioDao.registrar(usuario);

    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar (@RequestHeader(value = "Authorization") String token, @PathVariable Long id){

        if(validarToken(token)){
            usuarioDao.eliminar(id);
        }

    }
}
