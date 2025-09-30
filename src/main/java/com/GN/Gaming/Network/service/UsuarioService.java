package com.GN.Gaming.Network.service;

import com.GN.Gaming.Network.model.Usuario;
import com.GN.Gaming.Network.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Login
    public Usuario login(String nickname, String contrasena) {
        return usuarioRepository.findByNicknameAndContraseña(nickname, contrasena);
    }

    // Buscar usuario por ID
    public Optional<Usuario> findById(int idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    // Guardar o actualizar usuario
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Metodos para CRUD listado y busqueda

    // Listar todos los usuarios
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // Buscar por nombre (contiene, sin importar mayúsculas/minúsculas)
    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Buscar por correo
    public List<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreoContainingIgnoreCase(correo);
    }

    // Buscar por nickname
    public List<Usuario> buscarPorNickname(String nickname) {
        return usuarioRepository.findByNicknameContainingIgnoreCase(nickname);
    }

    public long countUsuarios() {
        return usuarioRepository.count(); // JpaRepository proporciona count()
    }
    // Método que combina búsqueda por texto (nickname/nombre/correo) y filtrado por rol (Cliente o Admin)
    public List<Usuario> buscarPorFiltroYRol(String filtro, String rol) {
        String q = (filtro == null || filtro.trim().isEmpty()) ? null : filtro.trim().toLowerCase();

        // 1) Obtener lista base filtrada por texto (si q == null -> devuelve todos)
        List<Usuario> candidatos = usuarioRepository.searchByFiltro(q);

        // 2) Si no se pidió rol, devolver la lista
        if (rol == null || rol.trim().isEmpty()) {
            return candidatos;
        }

        // Normalizar rol solicitado
        String rolReq = rol.trim().toLowerCase();

        if ("admin".equals(rolReq)) {
            // Solo los que en BD tengan rol = 'Admin' (case-insensitive)
            return candidatos.stream()
                    .filter(u -> u.getRol() != null && u.getRol().equalsIgnoreCase("admin"))
                    .collect(Collectors.toList());
        } else if ("cliente".equals(rolReq)) {
            // MAPEADO DE 'Cliente' a nombres comunes en BD.
            // Ajusta esta lista según cómo guardes los roles en BD.
            List<String> clientRoles = Arrays.asList("estandar", "cliente", "user", "usuario");

            return candidatos.stream()
                    .filter(u -> {
                        if (u.getRol() == null) return false;
                        return clientRoles.contains(u.getRol().trim().toLowerCase());
                    })
                    .collect(Collectors.toList());
        } else {
            // Si llega otro valor, devolvemos candidatos sin filtrar (o podrías devolver vacío).
            return candidatos;
        }
    }
}
