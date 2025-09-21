package mi_app.controllers;

import mi_app.models.Usuario;
import mi_app.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }


    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        usuario.setRol("USER"); // asigna rol por defecto
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioService.registrar(usuario);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }
}
