import java.util.ArrayList;

public class student {
    private String name;
    private ArrayList<Integer> grades;

    // Constructor
    public student(String name) {
        this.name = name;
        grades = new ArrayList<>();
    }

    // Add grade
    public void addGrade(int grade) {
        grades.add(grade);
    }

    // Calculate average
    public double getAverage() {
        if (grades.size() == 0)
            return 0;

        int sum = 0;
        for (int g : grades) {
            sum += g;
        }
        return (double) sum / grades.size();
    }

    // Convert student to file format
    public String toFileString() {
        String line = name;
        for (int g : grades) {
            line += "," + g;
        }
        return line;
    }

    // Getters
    public String getName() {
        return name;
    }

    public ArrayList<Integer> getGrades() {
        return grades;
    }
}
