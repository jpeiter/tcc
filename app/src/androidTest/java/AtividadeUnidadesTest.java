import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeUnidadesController;

public class AtividadeUnidadesTest {

    @Test
    public void caloriasValidator(){
        AtividadeUnidadesController controller = new AtividadeUnidadesController();
        double calorias = controller.calorias(80.0, 8050.0, (long) (60 * 60 * 1000));
        Assert.assertTrue(true);
    }
}
