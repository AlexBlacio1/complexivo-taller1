package mi_app.controllers;

import mi_app.models.Gasto;
import mi_app.models.Usuario;
import mi_app.service.GastoService;
import mi_app.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/gastos")
public class GastoController {

    private final GastoService gastoService;
    private final UsuarioService usuarioService;

    public GastoController(GastoService gastoService, UsuarioService usuarioService) {
        this.gastoService = gastoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/mios")
    public String misGastos(Model model, Authentication auth) {
        Usuario usuario = usuarioService.buscarPorEmail(auth.getName()).orElseThrow();
        List<Gasto> gastos = gastoService.listarPorUsuario(usuario.getId());
        double total = gastos.stream().mapToDouble(Gasto::getMonto).sum();

        model.addAttribute("gastos", gastos);
        model.addAttribute("total", total);
        return "gastos";
    }

    @GetMapping("/agregar")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("gasto", new Gasto());
        return "agregarGasto";
    }

    @PostMapping
    public String agregarGasto(@ModelAttribute Gasto gasto, Authentication auth) {
        Usuario usuario = usuarioService.buscarPorEmail(auth.getName()).orElseThrow();
        gasto.setUsuario(usuario);
        gastoService.guardar(gasto);
        return "redirect:/gastos/mios";
    }

    @PostMapping("/{id}")
    public String eliminarGasto(@PathVariable Long id, Authentication auth) {
        Usuario usuario = usuarioService.buscarPorEmail(auth.getName()).orElseThrow();
        gastoService.listarPorUsuario(usuario.getId())
                .stream()
                .filter(g -> g.getId().equals(id))
                .findFirst()
                .ifPresent(g -> gastoService.eliminar(id));
        return "redirect:/gastos/mios";
    }
}
