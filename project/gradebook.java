import java.util.*;
import java.io.*;

public class gradebook {

    static ArrayList<student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static final String FILE_NAME = "grades.txt";

    public static void main(String[] args) {
        loadFromFile();

        int choice;

        do {
            System.out.println("\n--- Student Gradebook ---");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade");
            System.out.println("3. Update Grade");
            System.out.println("4. View students");
            System.out.println("5. Save to File");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    addstudent();
                    break;
                case 2:
                    addGrade();
                    break;
                case 3:
                    updateGrade();
                    break;
                case 4:
                    viewstudents();
                    break;
                case 5:
                    saveToFile();
                    break;
                case 6:
                    saveToFile();
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option");
            }

        } while (choice != 6);
    }

    // Add a new student
    public static void addstudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        students.add(new student(name));
        System.out.println("Student added.");
    }

    // Add grade to existing student
    public static void addGrade() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        student student = findStudent(name);

        if (student != null) {
            System.out.print("Enter grade: ");
            int grade = scanner.nextInt();
            scanner.nextLine();
            student.addGrade(grade);
            System.out.println("Grade added.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // NEW: Update a student's grade
    public static void updateGrade() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        student student = findStudent(name);

        if (student == null) {
            System.out.println("student not found.");
            return;
        }

        ArrayList<Integer> grades = student.getGrades();

        if (grades.isEmpty()) {
            System.out.println("No grades to update.");
            return;
        }

        // Show grades
        System.out.println("Grades:");
        for (int i = 0; i < grades.size(); i++) {
            System.out.println(i + ": " + grades.get(i));
        }

        System.out.print("Enter grade index to update: ");
        int index = scanner.nextInt();

        if (index < 0 || index >= grades.size()) {
            System.out.println("Invalid index.");
            scanner.nextLine();
            return;
        }

        System.out.print("Enter new grade: ");
        int newGrade = scanner.nextInt();
        scanner.nextLine();

        grades.set(index, newGrade);
        System.out.println("Grade updated.");
    }

    // Display all students
    public static void viewstudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        for (student s : students) {
            System.out.println(
                    s.getName() + " | Average: " + s.getAverage());
        }
    }

    // Find student by name
    public static student findStudent(String name) {
        for (student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    // Save data to file
    public static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (student s : students) {
                writer.println(s.toFileString());
            }
            System.out.println("Data saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    // Load data from file
    public static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                student student = new student(parts[0]);

                for (int i = 1; i < parts.length; i++) {
                    student.addGrade(Integer.parseInt(parts[i]));
                }

                students.add(student);
            }
        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}
