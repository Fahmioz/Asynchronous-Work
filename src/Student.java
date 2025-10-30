/**
 * Student
 *
 * This class is a simple container for a student's information. I used it to
 * store an integer ID, the student's name, and the semester number. It has a
 * constructor to create a student, getter methods to read each field, and a
 * small `display` method that prints the student's details in a readable
 * format.
 *
 * Example: new Student(101, "Alice", 3);
 */
public class Student {
    private int id;
    private String name;
    private int semester;

    /**
     * Constructs a Student with the given id, name and semester.
     * @param id unique student id
     * @param name student's full name
     * @param semester the current semester number
     */
    public Student(int id, String name, int semester) {
        this.id = id;
        this.name = name;
        this.semester = semester;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSemester() {
        return semester;
    }

    /**
     * Prints a human-readable representation of the student record.
     */
    public void display() {
        System.out.printf("ID: %d | Name: %s | Semester: %d%n", id, name, semester);
    }
}
