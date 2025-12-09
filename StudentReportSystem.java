import java.util.Scanner;

public class StudentReportSystem {
    
    // Grading system constants (600 total marks = 100 per subject × 6 subjects)
    private static final double GRADE_A_MIN = 540.0;  // 90% and above
    private static final double GRADE_B_MIN = 480.0;  // 80% and above  
    private static final double GRADE_C_MIN = 420.0;  // 70% and above
    private static final double GRADE_D_MIN = 360.0;  // 60% and above
    private static final int NUM_SUBJECTS = 6;       // Fixed number of subjects
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=".repeat(60));
        System.out.println("      STUDENT SCORESHEET MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));
        
        // 1. SCHOOL DETAILS INPUT
        System.out.println("\nSECTION 1: SCHOOL INFORMATION");
        System.out.println("-".repeat(40));
        
        System.out.print("Enter School Name: ");
        String schoolName = scanner.nextLine();
        
        System.out.print("Enter Teacher Name: ");
        String teacherName = scanner.nextLine();
        
        System.out.print("Enter Grade Level: ");
        int gradeLevel = scanner.nextInt();
        
        System.out.print("Enter Academic Year: ");
        int academicYear = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        
        // 2. STUDENT DATA CONFIGURATION
        System.out.println("\nSECTION 2: STUDENT CONFIGURATION");
        System.out.println("-".repeat(40));
        
        System.out.print("Enter number of students: ");
        int studentCount = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        
        // Minimum requirement validation
        if (studentCount < 12) {
            System.out.println("⚠ Minimum requirement: 12 students");
            System.out.println("⚠ Setting student count to 12");
            studentCount = 12;
        }
        
        // Subject definitions
        String[] subjects = {
            "ENGLISH", "MATHEMATICS", "HISTORY", 
            "GEOGRAPHY", "SCIENCE", "PROGRAMMING"
        };
        
        // Data storage arrays
        String[] studentNames = new String[studentCount];
        double[][] studentMarks = new double[studentCount][NUM_SUBJECTS];
        double[] studentTotals = new double[studentCount];
        char[] studentGrades = new char[studentCount];
        
        // Statistical tracking arrays
        double[] subjectTotals = new double[NUM_SUBJECTS];
        int[] gradeDistribution = new int[5]; // A,B,C,D,F
        
        System.out.println("\nSECTION 3: STUDENT DATA ENTRY");
        System.out.println("-".repeat(40));
        System.out.println("Enter marks (0-100) for each subject:");
        
        // 3. DATA COLLECTION AND PROCESSING
        for (int studentIndex = 0; studentIndex < studentCount; studentIndex++) {
            System.out.println("\n" + "═".repeat(40));
            System.out.println("STUDENT " + (studentIndex + 1) + " of " + studentCount);
            System.out.println("═".repeat(40));
            
            // Student name input
            System.out.print("Student Full Name: ");
            studentNames[studentIndex] = scanner.nextLine();
            
            // Marks input for all subjects
            System.out.println("Enter marks for " + studentNames[studentIndex] + ":");
            for (int subjectIndex = 0; subjectIndex < NUM_SUBJECTS; subjectIndex++) {
                while (true) {
                    System.out.printf("%-12s: ", subjects[subjectIndex]);
                    double mark = scanner.nextDouble();
                    
                    // Input validation
                    if (mark >= 0 && mark <= 100) {
                        studentMarks[studentIndex][subjectIndex] = mark;
                        studentTotals[studentIndex] += mark;
                        subjectTotals[subjectIndex] += mark;
                        break;
                    } else {
                        System.out.println("   ⚠ Invalid! Enter 0-100");
                    }
                }
            }
            scanner.nextLine(); // Clear buffer
            
            // Grade calculation
            studentGrades[studentIndex] = calculateGrade(studentTotals[studentIndex]);
            
            // Update grade distribution
            updateGradeDistribution(studentGrades[studentIndex], gradeDistribution);
        }
        
        // 4. REPORT GENERATION
        System.out.println("\n" + "=".repeat(60));
        System.out.println("GENERATING COMPREHENSIVE REPORT...");
        System.out.println("=".repeat(60));
        
        generateReport(
            schoolName, teacherName, gradeLevel, academicYear,
            studentNames, studentMarks, studentTotals, studentGrades,
            subjects, subjectTotals, gradeDistribution, studentCount
        );
        
        scanner.close();
        
        System.out.println("\n" + "*".repeat(60));
        System.out.println("REPORT GENERATION COMPLETE");
        System.out.println("Thank you for using Student Scoresheet System!");
        System.out.println("*".repeat(60));
    }
    
    /**
     * Calculates letter grade based on total marks
     * @param total Total marks (out of 600)
     * @return char Grade (A, B, C, D, F)
     */
    private static char calculateGrade(double total) {
        if (total >= GRADE_A_MIN) return 'A';
        if (total >= GRADE_B_MIN) return 'B';
        if (total >= GRADE_C_MIN) return 'C';
        if (total >= GRADE_D_MIN) return 'D';
        return 'F';
    }
    
    /**
     * Updates grade distribution counter
     * @param grade Student's grade
     * @param distribution Grade distribution array
     */
    private static void updateGradeDistribution(char grade, int[] distribution) {
        switch (grade) {
            case 'A': distribution[0]++; break;
            case 'B': distribution[1]++; break;
            case 'C': distribution[2]++; break;
            case 'D': distribution[3]++; break;
            case 'F': distribution[4]++; break;
        }
    }
    
    /**
     * Generates formatted report with all statistics
     */
    private static void generateReport(
        String school, String teacher, int grade, int year,
        String[] names, double[][] marks, double[] totals, char[] grades,
        String[] subjects, double[] subjectTotals, 
        int[] gradeDist, int studentCount) {
        
        // Report Header
        System.out.println("\n\n" + "█".repeat(80));
        System.out.println("                     ACADEMIC PERFORMANCE REPORT");
        System.out.println("█".repeat(80));
        
        // Institutional Information
        System.out.println("\nINSTITUTIONAL DETAILS");
        System.out.println("─".repeat(40));
        System.out.printf("School: %s\n", school);
        System.out.printf("Class Teacher: %s\n", teacher);
        System.out.printf("Grade Level: %d\n", grade);
        System.out.printf("Academic Year: %d\n", year);
        System.out.printf("Total Students: %d\n", studentCount);
        
        // Main Data Table
        System.out.println("\n\nSTUDENT PERFORMANCE DATA");
        System.out.println("─".repeat(95));
        
        // Table Header
        System.out.printf("%-25s", "STUDENT NAME");
        for (String subject : subjects) {
            System.out.printf("%-12s", subject);
        }
        System.out.printf("%-12s%-10s\n", "TOTAL", "GRADE");
        System.out.println("─".repeat(95));
        
        // Student Rows
        double classTotal = 0;
        for (int i = 0; i < studentCount; i++) {
            System.out.printf("%-25s", names[i]);
            
            // Subject marks
            for (int j = 0; j < NUM_SUBJECTS; j++) {
                System.out.printf("%-12.2f", marks[i][j]);
            }
            
            // Total and grade
            System.out.printf("%-12.2f%-10s\n", totals[i], grades[i]);
            classTotal += totals[i];
        }
        
        // Statistical Summary
        System.out.println("\n\nSTATISTICAL ANALYSIS");
        System.out.println("─".repeat(95));
        
        // Subject-wise Analysis
        System.out.printf("%-25s", "SUBJECT AVERAGES:");
        for (int j = 0; j < NUM_SUBJECTS; j++) {
            double average = subjectTotals[j] / studentCount;
            System.out.printf("%-12.2f", average);
        }
        System.out.printf("%-12.2f\n", classTotal / studentCount);
        
        // Subject Totals
        System.out.printf("%-25s", "SUBJECT TOTALS:");
        for (int j = 0; j < NUM_SUBJECTS; j++) {
            System.out.printf("%-12.2f", subjectTotals[j]);
        }
        System.out.printf("%-12.2f\n", classTotal);
        
        // Grade Distribution
        System.out.println("\nGRADE DISTRIBUTION SUMMARY");
        System.out.println("─".repeat(40));
        System.out.printf("A (Excellent): %2d students | ", gradeDist[0]);
        System.out.printf("B (Good):     %2d students\n", gradeDist[1]);
        System.out.printf("C (Average):  %2d students | ", gradeDist[2]);
        System.out.printf("D (Pass):     %2d students\n", gradeDist[3]);
        System.out.printf("F (Fail):     %2d students\n", gradeDist[4]);
        
        // Performance Summary
        System.out.println("\nPERFORMANCE SUMMARY");
        System.out.println("─".repeat(40));
        double percentage = (classTotal / (studentCount * 600)) * 100;
        System.out.printf("Class Average: %.2f/600 (%.1f%%)\n", 
            classTotal / studentCount, percentage);
        System.out.printf("Highest Possible: 600.00 | Passing: 360.00+\n");
        
        System.out.println("\n" + "█".repeat(80));
        System.out.println("END OF REPORT");
        System.out.println("█".repeat(80));
    }
}
