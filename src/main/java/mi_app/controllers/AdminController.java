package mi_app.controllers;

import mi_app.service.GastoService;
import mi_app.models.Gasto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final GastoService gastoService;

    public AdminController(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    @GetMapping("/gastos")
    public String verTodosGastos(Model model) {
        List<Gasto> gastos = gastoService.listarTodos();
        double total = gastos.stream().mapToDouble(Gasto::getMonto).sum();
        model.addAttribute("gastos", gastos);
        model.addAttribute("total", total);
        return "gastos";
    }

    @PostMapping("/gastos/{id}/eliminar")
    public String eliminarGastoAdmin(@PathVariable Long id) {
        gastoService.eliminar(id);
        return "redirect:/admin/gastos";
    }

    @GetMapping("/gastos/mios")
    public String verGastos(Model model, Authentication auth) {
        String email = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<Gasto> gastos;
        if (isAdmin) {
            gastos = gastoService.listarTodos();
        } else {
            gastos = gastoService.listarPorUsuario(Long.valueOf(email));
        }

        double total = gastos.stream().mapToDouble(g -> g.getMonto() != null ? g.getMonto() : 0.0).sum();

        model.addAttribute("gastos", gastos);
        model.addAttribute("total", total);
        return "gastos";
    }
}
