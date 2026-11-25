package mn.edu.must.sqat;

/**
 * Calculates a letter grade and combines the five course assessment parts.
 *
 * Assessment maxima: attendance 10, laboratory 40, quizzes 10 each,
 * and final exam 30. The maximum total is 100 points.
 */
public final class GradeCalculator {

    private static final double MAX_TOTAL = 100.0;
    private static final double MAX_ATTENDANCE = 10.0;
    private static final double MAX_LABORATORY = 40.0;
    private static final double MAX_QUIZ = 10.0;
    private static final double MAX_FINAL_EXAM = 30.0;

    /**
     * Converts a score in the inclusive range [0, 100] to a letter grade.
     *
     * @param score numeric course score
     * @return A, B, C, D, or F
     * @throws IllegalArgumentException when score is non-finite or outside [0, 100]
     */
    public String letterGrade(double score) {
        requireInRange("score", score, MAX_TOTAL);

        if (score >= 90.0) {
            return "A";
        }
        if (score >= 80.0) {
            return "B";
        }
        if (score >= 70.0) {
            return "C";
        }
        if (score >= 60.0) {
            return "D";
        }
        return "F";
    }

    /**
     * Adds the assessment parts after validating each part against its maximum.
     */
    public double totalScore(double attendance, double laboratory,
                             double quizOne, double quizTwo, double finalExam) {
        requireInRange("attendance", attendance, MAX_ATTENDANCE);
        requireInRange("laboratory", laboratory, MAX_LABORATORY);
        requireInRange("quizOne", quizOne, MAX_QUIZ);
        requireInRange("quizTwo", quizTwo, MAX_QUIZ);
        requireInRange("finalExam", finalExam, MAX_FINAL_EXAM);

        return attendance + laboratory + quizOne + quizTwo + finalExam;
    }

    private static void requireInRange(String field, double value, double maximum) {
        if (!Double.isFinite(value) || value < 0.0 || value > maximum) {
            throw new IllegalArgumentException(
                    field + " must be a finite value from 0 to " + maximum);
        }
    }
}
