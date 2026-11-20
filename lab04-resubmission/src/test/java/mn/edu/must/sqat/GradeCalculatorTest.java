package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Үнэлгээ тооцоологчийн нэгжийн тест")
class GradeCalculatorTest {

    private final GradeCalculator calculator = new GradeCalculator();

    @Test
    @DisplayName("95 оноо A үнэлгээ болно")
    void highScoreGetsA() {
        // Arrange
        double score = 95.0;

        // Act
        String actual = calculator.letterGrade(score);

        // Assert
        assertEquals("A", actual);
    }

    @Test
    @DisplayName("45 оноо F үнэлгээ болно")
    void lowScoreGetsF() {
        // Arrange
        double score = 45.0;

        // Act
        String actual = calculator.letterGrade(score);

        // Assert
        assertEquals("F", actual);
    }

    @Test
    @DisplayName("90 ба 89.99 онооны зааг зөв ангилагдана")
    void gradeBoundaryAtNinety() {
        assertAll(
                () -> assertEquals("A", calculator.letterGrade(90.0)),
                () -> assertEquals("B", calculator.letterGrade(89.99))
        );
    }

    @Test
    @DisplayName("60 ба 59.99 онооны зааг зөв ангилагдана")
    void gradeBoundaryAtSixty() {
        assertAll(
                () -> assertEquals("D", calculator.letterGrade(60.0)),
                () -> assertEquals("F", calculator.letterGrade(59.99))
        );
    }

    @Test
    @DisplayName("0 болон 100 онооны зөвшөөрөгдөх захын утгууд")
    void acceptsMinimumAndMaximumScore() {
        assertAll(
                () -> assertEquals("F", calculator.letterGrade(0.0)),
                () -> assertEquals("A", calculator.letterGrade(100.0))
        );
    }

    @Test
    @DisplayName("Сөрөг оноо хүлээн авахгүй")
    void rejectsNegativeScore() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.letterGrade(-0.01));
    }

    @Test
    @DisplayName("100-аас их оноо хүлээн авахгүй")
    void rejectsScoreAboveOneHundred() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.letterGrade(100.01));
    }

    @Test
    @DisplayName("NaN болон хязгааргүй утгыг оноо гэж хүлээн авахгүй")
    void rejectsNonFiniteScore() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> calculator.letterGrade(Double.NaN)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> calculator.letterGrade(Double.POSITIVE_INFINITY))
        );
    }

    @Test
    @DisplayName("Бүрэлдэхүүн онооны дээд утгуудын нийлбэр 100")
    void sumsMaximumAssessmentValues() {
        double actual = calculator.totalScore(10, 40, 10, 10, 30);
        assertEquals(100.0, actual, 0.000001);
    }

    @Test
    @DisplayName("Ердийн бүрэлдэхүүн оноог зөв нэмнэ")
    void sumsTypicalAssessmentValues() {
        double actual = calculator.totalScore(8, 25, 7, 8, 20);
        assertEquals(68.0, actual, 0.000001);
    }

    @Test
    @DisplayName("Лабораторийн оноо дээд хязгаараас их бол алдаа өгнө")
    void rejectsLaboratoryOverflow() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.totalScore(10, 40.01, 10, 10, 30));
    }

    @Test
    @DisplayName("Сөрөг шалгалтын оноо алдаа өгнө")
    void rejectsNegativeExamScore() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.totalScore(10, 40, 10, 10, -1));
    }

    @ParameterizedTest(name = "{index}: {0} оноо -> {1}")
    @DisplayName("Онооны ангиллын бүх түвшнийг параметрээр шалгах")
    @CsvSource({
            "100.0, A",
            "90.0, A",
            "89.99, B",
            "80.0, B",
            "79.99, C",
            "70.0, C",
            "69.99, D",
            "60.0, D",
            "59.99, F",
            "0.0, F"
    })
    void mapsScoresToExpectedGrade(double score, String expectedGrade) {
        assertEquals(expectedGrade, calculator.letterGrade(score));
    }

    @ParameterizedTest(name = "{index}: нийт оноо {5}")
    @DisplayName("Онооны бүрэлдэхүүнүүдийг параметрээр нэмэх")
    @CsvSource({
            "0, 0, 0, 0, 0, 0",
            "10, 40, 10, 10, 30, 100",
            "5, 20, 5, 5, 15, 50",
            "8, 25, 7, 8, 20, 68"
    })
    void addsAssessmentParts(double attendance, double laboratory,
                             double quizOne, double quizTwo,
                             double finalExam, double expectedTotal) {
        assertEquals(expectedTotal,
                calculator.totalScore(attendance, laboratory, quizOne, quizTwo, finalExam),
                0.000001);
    }
}
