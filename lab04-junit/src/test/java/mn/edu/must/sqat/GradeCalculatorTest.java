package mn.edu.must.sqat;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;

public class GradeCalculatorTest {
    private final GradeCalculator calc = new GradeCalculator();

    @Test
    @DisplayName("95 оноо A дүн байх ёстой")
    void testA() {
        assertEquals("A", calc.letterGrade(95.0));
    }

    @Test
    @DisplayName("85 оноо B дүн байх ёстой")
    void testB() {
        assertEquals("B", calc.letterGrade(85.0));
    }

    @Test
    @DisplayName("75 оноо C дүн байх ёстой")
    void testC() {
        assertEquals("C", calc.letterGrade(75.0));
    }

    @Test
    @DisplayName("65 оноо D дүн байх ёстой")
    void testD() {
        assertEquals("D", calc.letterGrade(65.0));
    }

    @Test
    @DisplayName("30 оноо F дүн байх ёстой")
    void testF() {
        assertEquals("F", calc.letterGrade(30.0));
    }

    @Test
    @DisplayName("90 оноо яг A дүн байх ёстой (хязгаар)")
    void testNinetyIsA() {
        assertEquals("A", calc.letterGrade(90.0));
    }

    @Test
    @DisplayName("89.99 оноо B дүн байх ёстой (хязгаар)")
    void testAlmostNinetyIsB() {
        assertEquals("B", calc.letterGrade(89.99));
    }

    @Test
    @DisplayName("Буруу оноо (101) IllegalArgumentException шиднэ")
    void testInvalidScore() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101.0));
    }

    @Test
    @DisplayName("Буруу ирц (-1) IllegalArgumentException шиднэ")
    void testInvalidAttendance() {
        assertThrows(IllegalArgumentException.class, () -> calc.totalScore(-1, 40, 10, 10, 30));
    }

    @Test
    @DisplayName("Хэтэрсэн лаб (41) IllegalArgumentException шиднэ")
    void testInvalidLab() {
        assertThrows(IllegalArgumentException.class, () -> calc.totalScore(10, 41, 10, 10, 30));
    }

    @Test
    @DisplayName("Зөв нийлбэр оноог тооцох (100)")
    void testTotalScoreMax() {
        assertEquals(100.0, calc.totalScore(10, 40, 10, 10, 30));
    }

    @ParameterizedTest
    @CsvSource({
        "95,A", "90,A", "89.99,B", "80,B", "70,C", "60,D", "59.99,F", "0,F"
    })
    @DisplayName("Онооны хязгааруудыг шалгах parameterized тест")
    void letterGradeBoundaries(double score, String expected) {
        assertEquals(expected, calc.letterGrade(score));
    }

    @ParameterizedTest
    @CsvSource({
        "10,40,10,10,30,100.0",
        "0,0,0,0,0,0.0",
        "5,20,5,5,15,50.0"
    })
    @DisplayName("Нийлбэр онооны тооцооллыг шалгах parameterized тест")
    void totalScoreParameterized(double att, double lab, double q1, double q2, double exam, double expected) {
        assertEquals(expected, calc.totalScore(att, lab, q1, q2, exam));
    }
}