package com.GN.Gaming.Network.controller;

import com.GN.Gaming.Network.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {
        // Recuperar el usuario de sesión
        Usuario usuario = (Usuario) session.getAttribute("usuarioIn");

        if (usuario == null) {
            // Si no hay usuario logueado, lo mandamos a login
            return "redirect:/inicio";
        }

        // Pasar el usuario al modelo para Thymeleaf
        model.addAttribute("usuario", usuario);

        // Renderiza src/main/resources/templates/dashboard.html
        return "dashboard";
    }
}

