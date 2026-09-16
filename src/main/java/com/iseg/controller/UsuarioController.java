package com.iseg.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.iseg.dto.UsuarioDTO;
import com.iseg.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public UsuarioController(UsuarioService usuarioService, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute UsuarioDTO usuarioDTO,
                                   BindingResult result, Model model,
                                   HttpServletRequest request, HttpServletResponse response) {

        if (result.hasErrors()) {
            return "registro";
        }

        if (usuarioService.existeUsuario(usuarioDTO.getEmail())) {
            model.addAttribute("error", "Ya existe un usuario con ese email");
            return "registro";
        }

        if (!usuarioDTO.getPassword().equals(usuarioDTO.getConfirmPassword())) {
            model.addAttribute("error", "Las contrasenas no coinciden");
            return "registro";
        }

        usuarioService.registrarUsuario(usuarioDTO.getEmail(), usuarioDTO.getPassword());

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getPassword());
        Authentication authResult = authenticationManager.authenticate(authRequest);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        return "redirect:/";
    }
}
