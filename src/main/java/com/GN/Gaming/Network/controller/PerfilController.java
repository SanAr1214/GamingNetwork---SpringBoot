package com.GN.Gaming.Network.controller;

import com.GN.Gaming.Network.model.Usuario;
import com.GN.Gaming.Network.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    // GET -> mostrar perfil
    @GetMapping("/perfil")
    public String mostrarPerfil(@RequestParam("idUsuario") int idUsuario, Model model) {
        Optional<Usuario> usuarioOpt = usuarioService.findById(idUsuario);

        if (usuarioOpt.isPresent()) {
            model.addAttribute("usuario", usuarioOpt.get());
            return "perfil"; // <-- Thymeleaf buscará perfil.html en templates/
        } else {
            return "redirect:/inicioUsuario"; // redirige si no existe
        }
    }

    // POST -> actualizar perfil
    @PostMapping("/perfil")
    public String actualizarPerfil(
            @RequestParam("idUsuario") int idUsuario,
            @RequestParam("nombre") String nombre,
            @RequestParam("nickname") String nickname,
            @RequestParam("correo") String correo,
            @RequestParam(value = "contraseña", required = false) String contraseña,
            Model model) {

        Optional<Usuario> usuarioOpt = usuarioService.findById(idUsuario);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setNombre(nombre);
            usuario.setNickname(nickname);
            usuario.setCorreo(correo);

            if (contraseña != null && !contraseña.isEmpty()) {
                usuario.setContraseña(contraseña);
            }

            usuarioService.save(usuario); // guarda cambios usando el servicio

            return "redirect:/perfil?idUsuario=" + idUsuario; // vuelve a cargar el perfil
        } else {
            model.addAttribute("error", "No se pudo actualizar el perfil.");
            return "perfil"; // Thymeleaf buscará perfil.html
        }
    }
}
