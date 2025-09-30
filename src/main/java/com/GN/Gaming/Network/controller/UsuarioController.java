




package com.GN.Gaming.Network.controller;

import com.GN.Gaming.Network.model.Usuario;
import com.GN.Gaming.Network.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Mostrar formulario de registro
    @GetMapping("/registroUsuario")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registroUsuario";
    }

    // Procesar registro
    @PostMapping("/registroUsuario")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {

        // Verificar si el nickname o correo ya existen
        if (usuarioRepository.existsByNickname(usuario.getNickname())) {
            model.addAttribute("error", "El nickname ya está registrado ❌");
            return "registroUsuario";
        }
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            model.addAttribute("error", "El correo ya está registrado ❌");
            return "registroUsuario";
        }

        // Guardar usuario
        usuarioRepository.save(usuario);
        model.addAttribute("mensaje", "Usuario registrado correctamente ✅");
        model.addAttribute("usuario", new Usuario()); // limpiar formulario

        return "registroUsuario";
    }
}



