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
        // Arrange
        double aBoundary = 90.0;
        double bBoundary = 89.99;

        // Act
        String gradeAtNinety = calculator.letterGrade(aBoundary);
        String gradeBelowNinety = calculator.letterGrade(bBoundary);

        // Assert
        assertAll(
                () -> assertEquals("A", gradeAtNinety),
                () -> assertEquals("B", gradeBelowNinety)
        );
    }

    @Test
    @DisplayName("60 ба 59.99 онооны зааг зөв ангилагдана")
    void gradeBoundaryAtSixty() {
        // Arrange
        double dBoundary = 60.0;
        double fBoundary = 59.99;

        // Act
        String gradeAtSixty = calculator.letterGrade(dBoundary);
        String gradeBelowSixty = calculator.letterGrade(fBoundary);

        // Assert
        assertAll(
                () -> assertEquals("D", gradeAtSixty),
                () -> assertEquals("F", gradeBelowSixty)
        );
    }

    @Test
    @DisplayName("0 болон 100 онооны зөвшөөрөгдөх захын утгууд")
    void acceptsMinimumAndMaximumScore() {
        // Arrange
        double minimum = 0.0;
        double maximum = 100.0;

        // Act
        String minimumGrade = calculator.letterGrade(minimum);
        String maximumGrade = calculator.letterGrade(maximum);

        // Assert
        assertAll(
                () -> assertEquals("F", minimumGrade),
                () -> assertEquals("A", maximumGrade)
        );
    }

    @Test
    @DisplayName("Сөрөг оноо хүлээн авахгүй")
    void rejectsNegativeScore() {
        // Arrange
        double invalidScore = -0.01;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> calculator.letterGrade(invalidScore));
    }

    @Test
    @DisplayName("100-аас их оноо хүлээн авахгүй")
    void rejectsScoreAboveOneHundred() {
        // Arrange
        double invalidScore = 100.01;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> calculator.letterGrade(invalidScore));
    }

    @Test
    @DisplayName("NaN болон хязгааргүй утгыг оноо гэж хүлээн авахгүй")
    void rejectsNonFiniteScore() {
        // Arrange
        double nanScore = Double.NaN;
        double infiniteScore = Double.POSITIVE_INFINITY;

        // Act & Assert
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> calculator.letterGrade(nanScore)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> calculator.letterGrade(infiniteScore))
        );
    }

    @Test
    @DisplayName("Бүрэлдэхүүн онооны дээд утгуудын нийлбэр 100")
    void sumsMaximumAssessmentValues() {
        // Arrange
        double attendance = 10;
        double laboratory = 40;
        double quizOne = 10;
        double quizTwo = 10;
        double finalExam = 30;

        // Act
        double actual = calculator.totalScore(
                attendance, laboratory, quizOne, quizTwo, finalExam);

        // Assert
        assertEquals(100.0, actual, 0.000001);
    }

    @Test
    @DisplayName("Ердийн бүрэлдэхүүн оноог зөв нэмнэ")
    void sumsTypicalAssessmentValues() {
        // Arrange
        double attendance = 8;
        double laboratory = 25;
        double quizOne = 7;
        double quizTwo = 8;
        double finalExam = 20;

        // Act
        double actual = calculator.totalScore(
                attendance, laboratory, quizOne, quizTwo, finalExam);

        // Assert
        assertEquals(68.0, actual, 0.000001);
    }

    @Test
    @DisplayName("Лабораторийн оноо дээд хязгаараас их бол алдаа өгнө")
    void rejectsLaboratoryOverflow() {
        // Arrange
        double invalidLaboratory = 40.01;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> calculator.totalScore(10, invalidLaboratory, 10, 10, 30));
    }

    @Test
    @DisplayName("Сөрөг шалгалтын оноо алдаа өгнө")
    void rejectsNegativeExamScore() {
        // Arrange
        double invalidFinalExam = -1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> calculator.totalScore(10, 40, 10, 10, invalidFinalExam));
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
        // Arrange: score and expectedGrade are provided by CsvSource.

        // Act
        String actualGrade = calculator.letterGrade(score);

        // Assert
        assertEquals(expectedGrade, actualGrade);
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
        // Arrange: component scores and expectedTotal are provided by CsvSource.

        // Act
        double actualTotal = calculator.totalScore(
                attendance, laboratory, quizOne, quizTwo, finalExam);

        // Assert
        assertEquals(expectedTotal, actualTotal, 0.000001);
    }
}
