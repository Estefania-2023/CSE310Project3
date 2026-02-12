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
            System.out.println("3. View Students");
            System.out.println("4. Save to File");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addGrade();
                    break;
                case 3:
                    viewStudents();
                    break;
                case 4:
                    saveToFile();
                    break;
                case 5:
                    saveToFile();
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option");
            }

        } while (choice != 5);
    }

    // Function: Add student
    public static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        students.add(new student(name));
    }

    // Function: Add grade
    public static void addGrade() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        student student = findStudent(name);

        if (student != null) {
            System.out.print("Enter grade: ");
            int grade = scanner.nextInt();
            scanner.nextLine();
            student.addGrade(grade);
        } else {
            System.out.println("student not found");
        }
    }

    // Function: View students
    public static void viewStudents() {
        for (student s : students) {
            System.out.println(s.getName() +
                    " | Average: " + s.getAverage());
        }
    }

    // Function: Find student
    public static student findStudent(String name) {
        for (student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    // Additional Requirement: Write to file
    public static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (student s : students) {
                writer.println(s.toFileString());
            }
            System.out.println("Data saved.");
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    // Additional Requirement: Read from file
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
