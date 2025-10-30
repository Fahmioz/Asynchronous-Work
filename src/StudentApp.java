import java.util.Scanner;

/**
 * StudentApp
 *
 * This is the main application I wrote to manage students using a fixed-size
 * array. The program can insert Student objects (keeps them sorted by ID),
 * delete a student by ID, search for a student using binary search, and show
 * all students. The user interacts with a simple text menu.
 *
 * I also added a small pause after each operation so the result doesn't
 * disappear immediately — it waits for the user to press Enter before going
 * back to the menu. When running automatically (using an input file), include
 * extra blank lines to simulate pressing Enter.
 */
public class StudentApp {
    private Student[] students;
    private int count; // number of students currently stored
    private final int capacity;

    /**
     * Create a StudentApp with a fixed capacity.
     * @param capacity maximum number of students
     */
    public StudentApp(int capacity) {
        this.capacity = capacity;
        this.students = new Student[capacity];
        this.count = 0;
    }

    /**
     * Insert a student while keeping the array sorted by id.
     * If the id already exists, insertion is rejected.
     * @param s student to insert
     * @return true if inserted, false otherwise (full or duplicate id)
     */
    public boolean insert(Student s) {
        if (count >= capacity) {
            return false; // array full
        }
        // check for duplicate id
        if (binarySearchIndex(s.getId()) >= 0) {
            return false; // duplicate
        }

        // find insertion point (first index with id > s.id)
        int i = 0;
        while (i < count && students[i].getId() < s.getId()) {
            i++;
        }
        // shift right to make space
        for (int j = count; j > i; j--) {
            students[j] = students[j - 1];
        }
        students[i] = s;
        count++;
        return true;
    }

    /**
     * Delete a student by id. Returns true if deletion happened.
     * @param id student id to delete
     * @return true if deleted, false if not found
     */
    public boolean delete(int id) {
        int idx = binarySearchIndex(id);
        if (idx < 0) return false;
        // shift left to remove
        for (int i = idx; i < count - 1; i++) {
            students[i] = students[i + 1];
        }
        students[count - 1] = null;
        count--;
        return true;
    }

    /**
     * Perform binary search on the sorted array to find the index of a student id.
     * @param id student id to find
     * @return index (0..count-1) if found, otherwise -1
     */
    public int binarySearchIndex(int id) {
        int left = 0;
        int right = count - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midId = students[mid].getId();
            if (midId == id) return mid;
            if (midId < id) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    /**
     * Search for a student by id and return the Student object or null.
     * @param id student id
     * @return Student if found, otherwise null
     */
    public Student search(int id) {
        int idx = binarySearchIndex(id);
        return (idx >= 0) ? students[idx] : null;
    }

    /**
     * Displays all students currently in the array.
     */
    public void displayAll() {
        if (count == 0) {
            System.out.println("No student records to display.");
            return;
        }
        System.out.println("Current students (sorted by ID):");
        for (int i = 0; i < count; i++) {
            students[i].display();
        }
    }

    /**
     * Prompts the user to press Enter to return to the menu. Uses the provided
     * Scanner to consume the newline. When running non-interactively, ensure
     * the input stream contains an extra newline to satisfy this prompt.
     */
    public void promptPause(Scanner sc) {
        System.out.println();
        System.out.println("Press Enter to return to the menu...");
        sc.nextLine();
    }

    /**
     * Interactive menu-driven program entry point.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentApp app = new StudentApp(100); // capacity 100

        while (true) {
            System.out.println();
            System.out.println("=== Student Management Menu ===");
            System.out.println("1. Insert student");
            System.out.println("2. Delete student by ID");
            System.out.println("3. Search student by ID");
            System.out.println("4. Display all students");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                app.promptPause(sc);
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter student ID (integer): ");
                        int id = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Enter student name: ");
                        String name = sc.nextLine().trim();
                        System.out.print("Enter semester (integer): ");
                        int sem = Integer.parseInt(sc.nextLine().trim());
                        Student s = new Student(id, name, sem);
                        boolean ok = app.insert(s);
                        if (ok) System.out.println("Inserted successfully.");
                        else System.out.println("Insert failed (duplicate ID or capacity full).");
                    } catch (NumberFormatException nfe) {
                        System.out.println("Invalid number entered. Aborting insert.");
                    }
                    app.promptPause(sc);
                    break;
                case 2:
                    try {
                        System.out.print("Enter ID to delete: ");
                        int id = Integer.parseInt(sc.nextLine().trim());
                        boolean removed = app.delete(id);
                        if (removed) System.out.println("Student deleted.");
                        else System.out.println("Student with given ID not found.");
                    } catch (NumberFormatException nfe) {
                        System.out.println("Invalid ID input.");
                    }
                    app.promptPause(sc);
                    break;
                case 3:
                    try {
                        System.out.print("Enter ID to search: ");
                        int id = Integer.parseInt(sc.nextLine().trim());
                        Student found = app.search(id);
                        if (found != null) {
                            System.out.println("Student found:");
                            found.display();
                        } else {
                            System.out.println("Student not found.");
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("Invalid ID input.");
                    }
                    app.promptPause(sc);
                    break;
                case 4:
                    app.displayAll();
                    app.promptPause(sc);
                    break;
                case 5:
                    System.out.println("Exiting. Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Unknown option. Try again.");
                    app.promptPause(sc);
            }
        }
    }
}
