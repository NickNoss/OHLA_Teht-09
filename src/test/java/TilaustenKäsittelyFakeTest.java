import org.example.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TilaustenKäsittelyFakeTest {

    @Test
    public void testaaKäsittelijäWithFakeHinnoittelija() {
        // Arrange
        float alkuSaldo = 100.0f;
        float listaHinta = 30.0f;
        float alennus = 20.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));

        Asiakas as = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);
        IHinnoittelija hinnoittelija = new FakeHinnoittelija(alennus);

        // Act
        TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
        käsittelijä.setHinnoittelija(hinnoittelija);
        käsittelijä.käsittele(new Tilaus(as, tuote));

        // Assert
        assertEquals(loppuSaldo, as.getSaldo(), 0.001);
    }
}
