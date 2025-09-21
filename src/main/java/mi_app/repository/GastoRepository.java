package mi_app.repository;

import mi_app.models.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastoRepository  extends JpaRepository<Gasto, Long> {
    List<Gasto> findByUsuarioId(Long usuarioId);

    @Query("SELECT g FROM Gasto g JOIN FETCH g.usuario")
    List<Gasto> findAllWithUsuario();
}
