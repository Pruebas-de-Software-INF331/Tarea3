package cl.fidelidad.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NivelFidelidadTest {

    @Test
    void nivelBronceSeAsignaCorrectamente() {
        assertEquals(NivelFidelidad.BRONCE, NivelFidelidad.obtenerNivel(0));
        assertEquals(NivelFidelidad.BRONCE, NivelFidelidad.obtenerNivel(499));
    }

    @Test
    void nivelPlataSeAsignaCorrectamente() {
        assertEquals(NivelFidelidad.PLATA, NivelFidelidad.obtenerNivel(500));
        assertEquals(NivelFidelidad.PLATA, NivelFidelidad.obtenerNivel(1499));
    }

    @Test
    void nivelOroSeAsignaCorrectamente() {
        assertEquals(NivelFidelidad.ORO, NivelFidelidad.obtenerNivel(1500));
        assertEquals(NivelFidelidad.ORO, NivelFidelidad.obtenerNivel(2999));
    }

    @Test
    void nivelPlatinoSeAsignaCorrectamente() {
        assertEquals(NivelFidelidad.PLATINO, NivelFidelidad.obtenerNivel(3000));
        assertEquals(NivelFidelidad.PLATINO, NivelFidelidad.obtenerNivel(Integer.MAX_VALUE));
    }

    @Test
    void nivelPorDefectoEsBronce() {
        // Probar un valor fuera de rango negativo
        assertEquals(NivelFidelidad.BRONCE, NivelFidelidad.obtenerNivel(-50));
    }

    @Test
    void multiplicadoresSonCorrectos() {
        assertEquals(1.0, NivelFidelidad.BRONCE.getMultiplicador());
        assertEquals(1.2, NivelFidelidad.PLATA.getMultiplicador());
        assertEquals(1.5, NivelFidelidad.ORO.getMultiplicador());
        assertEquals(2.0, NivelFidelidad.PLATINO.getMultiplicador());
    }
}
