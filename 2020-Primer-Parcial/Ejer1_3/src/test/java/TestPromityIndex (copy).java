import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPromityIndex {

    String[] elements = new String[]{"Ana", "Carlos", "Juan", "Yolanda"};
    ProximityIndex index = new ProximityIndex();

    int testCounter = 0;

    @BeforeAll
    static void initAll(){
        System.out.println("Inicio de los Testeo de Proximity Index");
    }

    /*  Testeos Posibles
        1. Que search devuelva NULL
        2. La distancia sea positiva
        3. La distancia sea negativa
        4. La distancia sea positiva se pase de los limites
        5. La distancia sea negativa se pase de los limites
        6. La distancia sea cero
    */
    @BeforeEach
    void proximityIndexSetUp(){
        System.out.println("Inicio del Basic Test " + testCounter);
        index.initialize(elements);
    }
    @Test
    void returnNull(){
        assertEquals(null, index.search("XXX", -4));
    }

    @Test
    void positiveDistanceWithinLimits(){
        assertEquals("Yolanda", index.search("Carlos", 2));
    }

    @Test
    void negativeDistanceWithinLimits(){
        assertEquals("Ana", index.search("Juan", -2));
    }

    @Test
    void positiveDistanceNotWithinLimits(){
        assertEquals("Juan", index.search("Ana", 14));
    }

    @Test
    void negativeDistanceNotWithinLimits(){
        assertEquals("Yolanda", index.search("Ana", -17));
    }
    @AfterEach
    void testFinished() {
        System.out.println("Fin del Basic test " + testCounter);
    }
    @AfterAll
    static void allTestFinished() {
        System.out.println("Fin de los Basic Tests");
    }
}
