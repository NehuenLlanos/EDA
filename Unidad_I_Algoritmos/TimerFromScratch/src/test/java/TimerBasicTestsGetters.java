import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimerBasicTestsGetters {
    @BeforeAll
    static void initAll(){
        System.out.println("Inicio de los Basic Tests");
    }
    private static int testCounter = 0;
    private MyTimer timer;
    private final long initTime = 100;

    /*  Testeos Posibles
        1. Que de cero
        2. Probar si los millis dan correctamente.
        3. Probar si los segundos dan correctamente.
        4. Probar si los minutos dan correctamente.
        5. Probar si las horas dan correctamente.
        6. Probar si los dias dan correctamente.
        7. Probar si un numemero de millis da correcto en todos los parametros mencionados prev.
    */
    @BeforeEach
    void timerSetUp(){
        timer = new MyTimer(initTime);
        ++testCounter;
        System.out.println("Inicio del Basic Test " + testCounter);
    }
    // Testeo por si end < start
    @Test
    void shouldThrowException(){
        assertThrows(RuntimeException.class, () -> { timer.stop(3); });
    }
    // Testeo por si end < start con mensaje
    @Test
    void shouldThrowExceptionWithMessage(){
        Throwable except= assertThrows(RuntimeException.class, () -> {timer.stop(3);});
        assertEquals("End should be higher than start.", except.getMessage());
    }
    @Test
    void TestZero(){
        timer.stop(initTime);
        assertEquals(0, timer.getElapsedTime());
        assertEquals(0.000000, timer.getSeconds());
        assertEquals(0, timer.getMin());
        assertEquals(0, timer.getHour());
        assertEquals(0, timer.getDay());
    }
    @Test
    void TestMillis(){
        timer.stop(initTime + (long)100);
        assertEquals(100, timer.getElapsedTime());
    }
    @Test
    void TestSeconds(){
        timer.stop(initTime + 1000);
        assertEquals(1, timer.getSeconds());
    }
    @Test
    void TestMinutes(){
        timer.stop(initTime + 10*60*1000);
        assertEquals(10, timer.getMin());
    }
    @Test
    void TestHours(){
        timer.stop(initTime + 360*60*1000);
        assertEquals(6, timer.getHour());
    }
    @Test
    void TestDays(){
        timer.stop(initTime + 259200000);
        assertEquals(3, timer.getDay());
    }
    @Test
    void TestIntegrated(){
        timer.stop(initTime + 93623040);
        assertEquals(93623040, timer.getElapsedTime());
        assertEquals(23.040000, timer.getSeconds());
        assertEquals(0, timer.getMin());
        assertEquals(2, timer.getHour());
        assertEquals(1, timer.getDay());
    }
    @AfterEach
    void tearDown() {
        System.out.println("Fin del Basic test " + testCounter);
    }
    @AfterAll
    static void tearDownAll() {
        System.out.println("Fin de los Basic Tests");
    }
}
