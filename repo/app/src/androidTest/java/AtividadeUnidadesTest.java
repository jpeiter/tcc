import androidx.test.platform.app.InstrumentationRegistry;

import com.digidemic.unitof.UnitOf;

import org.junit.Assert;
import org.junit.Test;

import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeResourceController;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeUnidadesController;

public class AtividadeUnidadesTest {

    double distanciaMetros = new UnitOf.Length().fromKilometers(24).toMeters();
    long tempoMillis = (long) new UnitOf.Time().fromMinutes(60 * 2 + 50 ).toMilliseconds();
    double peso = 115;

    @Test
    public void calorias() throws Exception {
        AtividadeUnidadesController controller = new AtividadeUnidadesController();
        long expected = 3079;
        long actual = (long) controller.calorias(peso, distanciaMetros, tempoMillis);
        Assert.assertSame(expected, actual);
    }

    @Test
    public void ritmo() throws Exception {
        AtividadeUnidadesController controller = new AtividadeUnidadesController();
        int expected = 7 * 60 + 4;
        int actual = controller.ritmo(distanciaMetros, tempoMillis);
        Assert.assertSame(expected, actual);
    }

    @Test
    public void ritmoResource() throws Exception {
        AtividadeResourceController controller = new AtividadeResourceController(InstrumentationRegistry.getInstrumentation().getContext());
        String expected = "7min 4s";
        String actual = controller.ritmo(distanciaMetros, tempoMillis);
        Assert.assertSame(expected, actual);
    }
}
