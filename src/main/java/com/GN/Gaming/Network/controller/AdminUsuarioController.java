package com.GN.Gaming.Network.controller;

import com.GN.Gaming.Network.model.Usuario;
import com.GN.Gaming.Network.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class AdminUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(
            @RequestParam(value = "rol", required = false) String rol,
            @RequestParam(value = "filtro", required = false) String filtro,
            Model model) {

        List<Usuario> usuarios;

        boolean filtroVacio = (filtro == null || filtro.trim().isEmpty());
        boolean rolVacio = (rol == null || rol.trim().isEmpty());

        if (filtroVacio && rolVacio) {
            usuarios = usuarioService.findAll();
        } else {
            // Busca por texto (nickname, nombre, correo) y luego filtra por rol si se pidió
            usuarios = usuarioService.buscarPorFiltroYRol(filtro, rol);
        }

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("filtro", filtro == null ? "" : filtro);
        model.addAttribute("rolSeleccionado", rol == null ? "" : rol);

        return "usuarios";
    }
}


