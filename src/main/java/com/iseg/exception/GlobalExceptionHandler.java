package com.iseg.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String recursoNoEncontrado(RecursoNoEncontradoException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String paginaNoEncontrada(NoResourceFoundException ex, Model model) {
        model.addAttribute("mensaje", "La pagina solicitada no existe.");
        return "error/404";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String datosDuplicados(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error",
                "No se pudo guardar: el RUT o el email ya estan registrados.");
        return "redirect:/estudiantes";
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String recursoNoEncontradoAlEliminar(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error",
                "El estudiante que intentas eliminar ya no existe.");
        return "redirect:/estudiantes";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String violacionConstraint(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error",
                "No se pudo guardar: revisa que todos los campos cumplan sus validaciones.");
        return "redirect:/estudiantes";
    }
}
