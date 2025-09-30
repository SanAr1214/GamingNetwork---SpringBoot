package com.GN.Gaming.Network.controller;

import com.GN.Gaming.Network.model.Usuario;
import com.GN.Gaming.Network.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    // GET -> muestra el formulario de login
    @GetMapping("/inicio")
    public String mostrarLogin(@RequestParam(value = "nickname", required = false) String nickname,
                               Model model) {
        if (nickname != null) {
            model.addAttribute("nickname", nickname);
        }
        return "inicioUsuario"; // resuelve src/main/resources/templates/inicioUsuario.html
    }

    // POST -> procesa el login (similar al doPost)
    @PostMapping("/inicio")
    public String login(
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "contrasena", required = false) String contrasena,
            @RequestParam(value = "recordarme", required = false) String recordarme,
            HttpSession session,
            Model model) {

        // Validación básica de entrada
        if (nickname == null || nickname.trim().isEmpty() ||
                contrasena == null || contrasena.trim().isEmpty()) {
            model.addAttribute("error", "Por favor ingresa usuario y contraseña.");
            model.addAttribute("nickname", nickname);
            return "inicioUsuario";
        }

        Usuario usuarioObj = usuarioService.login(nickname.trim(), contrasena);

        if (usuarioObj != null) {
            session.setAttribute("usuarioIn", usuarioObj);

            // Si quieres manejar "recordarme" con cookies/jwt, implementarlo aquí.
            // Ejemplo: if (recordarme != null) { /* crear cookie persistente */ }

            // Redirige al dashboard. Ajusta la ruta si tu dashboard está en otra URL (/vista/dashboard por ejemplo).
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            model.addAttribute("nickname", nickname);
            return "inicioUsuario";
        }
    }
}