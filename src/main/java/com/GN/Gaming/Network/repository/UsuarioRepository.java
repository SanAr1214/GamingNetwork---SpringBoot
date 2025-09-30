package com.GN.Gaming.Network.repository;

import com.GN.Gaming.Network.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/*
 * 🔹 Métodos que YA tiene gracias a JpaRepository:
 * - save(usuario)         → Inserta o actualiza un usuario
 * - findById(id)          → Busca por id
 * - findAll()             → Lista todos los usuarios
 * - deleteById(id)        → Elimina por id
 *
 * 🔹 Métodos extra que definimos (opcionales):
 * Spring genera las consultas automáticamente según el nombre del método.
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //Validar usuario para login
    Usuario findByNicknameAndContraseña(String nickname, String contraseña);

    // 🔍 Buscar usuario por correo
    Usuario findByCorreo(String correo);

    // 🔍 Buscar usuario por nickname
    Usuario findByNickname(String nickname);

    // 🔍 Verificar si un nickname ya existe
    boolean existsByNickname(String nickname);

    // 🔍 Verificar si un correo ya existe
    boolean existsByCorreo(String correo);

    // Metodos para filtros
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findByCorreoContainingIgnoreCase(String correo);
    List<Usuario> findByNicknameContainingIgnoreCase(String nickname);

    // búsqueda combinada por filtro de texto (nombre, correo, nickname)
    // si el filtro es null, devuelve todos
    @Query("SELECT u FROM Usuario u " +
            "WHERE (:filtro IS NULL OR " +
            "LOWER(u.nombre) LIKE CONCAT('%', LOWER(:filtro), '%') OR " +
            "LOWER(u.correo) LIKE CONCAT('%', LOWER(:filtro), '%') OR " +
            "LOWER(u.nickname) LIKE CONCAT('%', LOWER(:filtro), '%'))")
    List<Usuario> searchByFiltro(@Param("filtro") String filtro);
}


