import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {

    private static final String url = "jdbc:mysql://localhost:3306/java";
    private static final String user = "root";
    private static final String password = "Satish@3002";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== STUDENT DATABASE MENU =====");
            System.out.println("1. Add New Student (Insert)");
            System.out.println("2. View All Students (Read)");
            System.out.println("3. Update Student City (Update)");
            System.out.println("4. Delete a Student (Delete)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> insertData(scanner);
                case 2 -> readData();
                case 3 -> updateData(scanner);
                case 4 -> deleteData(scanner);
                case 5 -> {
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void insertData(Scanner scanner) {

        String str = "Insert INTO studentInfo (id, sname, sage, scity) values (?, ?, ?, ?)";


        System.out.println("Enter the details of the student");

        System.out.println("Enter the id.no");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the student name");
        String name = scanner.nextLine();
        scanner.nextLine();

        System.out.println("Enter the student age");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the student name");
        String city = scanner.nextLine();

    
        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(str)) {

                pstmt.setInt(1, id);
                pstmt.setString(2,  name);
                pstmt.setInt(3, age);
                pstmt.setString(4, city);

                int result = pstmt.executeUpdate();
                if(result > 0){
                    System.out.println("Data had been updated");
                }

                conn.close();
        }catch(SQLException e){
            System.out.println("Issue in updating the data");
        }
    }

    private static void readData(){
        String sql = "Select * from studentInfo";

        try(Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

               System.out.println("\nID \t Name \t\t Age \t City");
            System.out.println("----------------------------------------");
                while(rs.next()){
                   System.out.println( rs.getInt(1) + " \t"+
                    rs.getString("sname") + " \t " + 
                                   rs.getInt("sage") + " \t " + 
                                   rs.getString(4));
                }
                conn.close();

        }catch(SQLException e){
            System.out.println("issue in reading data");
        }
    }


    private static void updateData(Scanner scanner){
        String sql = "Update studentInfo SET scity = ? WHERE id = ?";

        System.out.print("Enter Student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter New City: ");
        String newCity = scanner.nextLine();

        try(Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, newCity);
            pstmt.setInt(2, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Student city updated successfully!");
            else System.out.println("Student ID not found.");

            conn.close();
            }catch(SQLException e){
                System.out.println("Error updating data: " + e.getMessage());
            }
    }

    private static void deleteData(Scanner scanner){
        String sql = "Delete FROM studentInfo WHERE id = ?";
        
        System.out.print("Enter Student ID to delete: ");
        int id = scanner.nextInt();
        

        try(Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setInt(1, id);

                int result = pstmt.executeUpdate();
                if(result > 0){
                    System.out.println("data had been deleted which consists id of "+id);
                }else{
                    System.out.println("id not found");
                }

                conn.close();
            }catch(SQLException e){
                System.out.println("issue in deleting entry");
            }
    }


}
