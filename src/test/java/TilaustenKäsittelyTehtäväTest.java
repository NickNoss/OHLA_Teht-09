import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TilaustenKäsittelyTehtäväTest {

    @Mock
    IHinnoittelija hinnoittelija;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testaaKäsittelijäHintaAlleSata() {
        // Arrange
        float alkuSaldo = 100.0f;
        float listaHinta = 60.0f;
        float alennus = 15.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));
        Asiakas as = new Asiakas(alkuSaldo);
        Tuote t = new Tuote("Housut", listaHinta);

        // Record
        when(hinnoittelija.getAlennusProsentti(as, t))
                .thenReturn(alennus);

        // Act
        TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
        käsittelijä.setHinnoittelija(hinnoittelija);
        käsittelijä.käsittele(new Tilaus(as, t));

        // Assert
        assertEquals(loppuSaldo, as.getSaldo(), 0.001);
        verify(hinnoittelija, times(2))
                .getAlennusProsentti(as, t);
    }

    @Test
    public void testaaKäsittelijäHintaYliSata() {
        // Arrange
        float alkuSaldo = 500.0f;
        float listaHinta = 350.0f;
        float alennus = 15.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - (alennus + 5) / 100));
        Asiakas as = new Asiakas(alkuSaldo);
        Tuote t = new Tuote("ANC Kuulokkeet", listaHinta);

        // Record
        when(hinnoittelija.getAlennusProsentti(as, t))
                .thenReturn(alennus + 5);

        // Act
        TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
        käsittelijä.setHinnoittelija(hinnoittelija);
        käsittelijä.käsittele(new Tilaus(as, t));

        // Assert
        assertEquals(loppuSaldo, as.getSaldo(), 0.001);
        verify(hinnoittelija, times(2))
                .getAlennusProsentti(as, t);
    }
}
