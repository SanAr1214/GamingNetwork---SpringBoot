package com.GN.Gaming.Network.controller;

import com.GN.Gaming.Network.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EliminarUsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/eliminarUsuario")
    public String eliminarUsuario(@RequestParam("idUsuario") int idUsuario, Model model) {
        try {
            usuarioRepository.deleteById(idUsuario);
            return "redirect:/inicioUsuario"; // lo llevamos al inicio
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo eliminar el usuario.");
            return "redirect:/perfil?idUsuario=" + idUsuario;
        }
    }
}
