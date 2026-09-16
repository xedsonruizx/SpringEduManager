package com.iseg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iseg.model.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    @Query("""
            SELECT e FROM Estudiante e
            WHERE LOWER(e.rut)             LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(e.nombre)          LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(e.apellidoPaterno) LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(e.apellidoMaterno) LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(e.email)           LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(e.ciudad)          LIKE LOWER(CONCAT('%', :texto, '%'))
            """)
    List<Estudiante> buscarPorTexto(@Param("texto") String texto);

    List<Estudiante> findByActivoTrue();
}
