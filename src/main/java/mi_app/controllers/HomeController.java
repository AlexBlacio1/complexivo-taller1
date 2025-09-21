package mi_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("titulo", "Bienvenido al Control de Gastos");
        model.addAttribute("descripcion", "Gestiona tus gastos personales de forma fácil y segura.");
        return "home";
    }
}