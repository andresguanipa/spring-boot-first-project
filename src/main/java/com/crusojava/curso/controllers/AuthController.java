package com.crusojava.curso.controllers;

import com.crusojava.curso.dao.UsuarioDao;
import com.crusojava.curso.models.Usuario;
import com.crusojava.curso.utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private Jwt jwt;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login (@RequestBody Usuario usuario){

        Usuario user = usuarioDao.verificarCredenciales(usuario);

        if(user != null){

            String token = jwt.create(String.valueOf(usuario.getId()), usuario.getEmail());

            return token;
        }

        return "FAIL";

    }

}
