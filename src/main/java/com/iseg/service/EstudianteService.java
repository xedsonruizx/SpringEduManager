package com.iseg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iseg.model.Estudiante;
import com.iseg.repository.EstudianteRepository;
import com.iseg.util.RutUtil;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> buscarPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    public Estudiante guardar(Estudiante estudiante) {
        estudiante.setRut(RutUtil.formatear(estudiante.getRut()));
        return estudianteRepository.save(estudiante);
    }

    public void eliminar(Long id) {
        estudianteRepository.deleteById(id);
    }

    public List<Estudiante> buscar(String texto) {
        if (texto == null || texto.isBlank()) {
            return estudianteRepository.findAll();
        }
        return estudianteRepository.buscarPorTexto(texto.trim());
    }

    public List<Estudiante> listarActivos() {
        return estudianteRepository.findByActivoTrue();
    }
}
