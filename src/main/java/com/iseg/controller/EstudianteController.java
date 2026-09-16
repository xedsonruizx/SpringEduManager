package com.iseg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iseg.exception.RecursoNoEncontradoException;
import com.iseg.model.Estudiante;
import com.iseg.service.EstudianteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        return "estudiantes/listar";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam(required = false) String texto, Model model) {
        model.addAttribute("estudiantes", estudianteService.buscar(texto));
        model.addAttribute("texto", texto);
        return "estudiantes/listar";
    }

    @GetMapping("/ver/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Estudiante estudiante = estudianteService.buscarPorId(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + id));
        model.addAttribute("estudiante", estudiante);
        return "estudiantes/detalle";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "estudiantes/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Estudiante estudiante = estudianteService.buscarPorId(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + id));
        model.addAttribute("estudiante", estudiante);
        return "estudiantes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Estudiante estudiante,
                          BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "estudiantes/formulario";
        }

        boolean esNuevo = estudiante.getId() == null;
        estudianteService.guardar(estudiante);

        redirectAttributes.addFlashAttribute("mensaje",
                esNuevo ? "Estudiante registrado correctamente" : "Estudiante actualizado correctamente");
        return "redirect:/estudiantes";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (estudianteService.buscarPorId(id).isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El estudiante que intentas eliminar ya no existe.");
            return "redirect:/estudiantes";
        }

        estudianteService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Estudiante eliminado correctamente");
        return "redirect:/estudiantes";
    }
}
