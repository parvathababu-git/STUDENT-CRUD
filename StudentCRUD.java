import java.sql.*;
import java.util.Scanner;

class StudentCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";   // your MySQL username
    private static final String PASS = "root";   // your MySQL password

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // CREATE
    public void addStudent(String name, int age, String grade) {
        String query = "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, grade);
            ps.executeUpdate();
            System.out.println("✅ Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    public void viewStudents() {
        String query = "SELECT * FROM students";
        try (Connection con = connect(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            System.out.println("\n--- Student Records ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Age: %d | Grade: %s%n",
                        rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getString("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateStudent(int id, String name, int age, String grade) {
        String query = "UPDATE students SET name=?, age=?, grade=? WHERE id=?";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, grade);
            ps.setInt(4, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("✅ Student updated successfully!");
            else System.out.println("⚠ No student found with ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteStudent(int id) {
        String query = "DELETE FROM students WHERE id=?";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("✅ Student deleted successfully!");
            else System.out.println("⚠ No student found with ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // MAIN MENU
    public static void main(String[] args) {
        StudentCRUD crud = new StudentCRUD();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Student CRUD Menu ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter Grade: ");
                    String grade = sc.nextLine();
                    crud.addStudent(name, age, grade);
                    break;

                case 2:
                    crud.viewStudents();
                    break;

                case 3:
                    System.out.print("Enter Student ID to Update: ");
                    int uid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Name: ");
                    String uname = sc.nextLine();
                    System.out.print("Enter New Age: ");
                    int uage = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Grade: ");
                    String ugrade = sc.nextLine();
                    crud.updateStudent(uid, uname, uage, ugrade);
                    break;

                case 4:
                    System.out.print("Enter Student ID to Delete: ");
                    int did = sc.nextInt();
                    crud.deleteStudent(did);
                    break;

                case 5:
                    System.out.println("🚀 Exiting... Goodbye!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }
}

